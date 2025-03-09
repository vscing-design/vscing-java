package com.vscing.api.receiver;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.vscing.api.service.OrderService;
import com.vscing.common.api.ResultCode;
import com.vscing.common.service.RedisService;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.common.service.supplier.SupplierService;
import com.vscing.common.service.supplier.SupplierServiceFactory;
import com.vscing.common.utils.JsonUtils;
import com.vscing.common.utils.StringUtils;
import com.vscing.model.entity.Order;
import com.vscing.model.enums.AppletTypeEnum;
import com.vscing.model.http.HttpOrder;
import com.vscing.model.mapper.OrderMapper;
import com.vscing.model.mq.SyncCodeMq;
import com.vscing.mq.config.DelayRabbitMQConfig;
import com.vscing.mq.service.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * DelayMessageReceiver
 *
 * @author vscing
 * @date 2025/3/8 18:25
*/
@Slf4j
@Component
public class DelayMessageReceiver {

  private static final String ORDER_STATUS_GENERATE_SUCCESS = "GENERATE_SUCCESS";

  @Autowired
  private SupplierServiceFactory supplierServiceFactory;

  @Autowired
  private AppletServiceFactory appletServiceFactory;

  @Autowired
  private OrderService orderService;

  @Autowired
  private RedisService redisService;

  @Autowired
  private RabbitMQService rabbitMQService;

  @Autowired
  private OrderMapper orderMapper;

  /**
   * 生成18位订单编号:8位日期+2位平台号码+2位支付方式+6位以上自增id
   */
  private String generateOrderSn() {
    // 获取当前时间戳，格式为yyyyMMddHHmmssSSS（17位）
    String timestamp = DateUtil.now().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "") + DateUtil.format(DateUtil.date(), "SSS");
    // 生成5位随机数字，用于补足18位
    String randomNum = RandomUtil.randomNumbers(5);
    // 组合生成订单号
    return "HY-TD" + (timestamp + randomNum).substring(0, 18);
  }

  /**
   * 同步场次码延迟队列 手动应答，再执行一个消息。
   */
  @RabbitListener(queues = DelayRabbitMQConfig.SYNC_CODE_QUEUE, ackMode = "MANUAL")
  public void receiveSyncCodeMessage(Message message, Channel channel,
                                     @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
    try {
      int tag = 0;
      // 处理同步场次码消息
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.error("同步场次码延迟队列消息: {}", msg);
      if(StringUtils.isEmpty(msg)) {
        throw new Exception("消息体错误");
      }
      SyncCodeMq syncCodeMq = JsonUtils.parseObject(msg, SyncCodeMq.class);
      if(syncCodeMq == null) {
        throw new Exception("解析消息体错误");
      }
      log.error("同步场次码延迟队列消息 orderId: {}, num: {}", syncCodeMq.getOrderId(), syncCodeMq.getNum());
      // 查询订单信息
      Order order = orderMapper.selectById(syncCodeMq.getOrderId());
      // 准备请求参数
      Map<String, String> params = new HashMap<>();
      params.put("tradeNo", order.getOrderSn());
      SupplierService supplierService = supplierServiceFactory.getSupplierService("jfshou");
      // 发送请求并获取响应
      String responseBody = supplierService.sendRequest("/order/query", params);
      // 将 JSON 字符串解析为 JsonNode 对象
      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
      Integer code = (Integer) responseMap.getOrDefault("code", 0);
      String apiMessage = (String) responseMap.getOrDefault("message", "未知错误");
      if(code != ResultCode.SUCCESS.getCode()) {
        log.error("同步场次码延迟队列 code: {}, message: {}", code, apiMessage);
      }
      Object data = responseMap.get("data");
      if(data == null){
        tag = 1;
      }
      HttpOrder httpOrder = objectMapper.convertValue(data, HttpOrder.class);
      if(httpOrder == null || !ORDER_STATUS_GENERATE_SUCCESS.equals(httpOrder.getOrderStatus())){
        tag = 1;
      }
      // 更新数据
      boolean result = orderService.supplierOrder(httpOrder);
      log.error("同步场次码延迟队列数据更新结果：{}", result);
      // 判断是否需要重试
      if(tag == 1 && syncCodeMq.getNum() >= 20) {
        // 发送mq异步处理 退款
        rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.REFUND_ROUTING_KEY, syncCodeMq.getOrderId().toString(), 2*60 *1000);
      } else if(tag == 1) {
        syncCodeMq.setNum(syncCodeMq.getNum() + 1);
        String newMsg = JsonUtils.toJsonString(syncCodeMq);
        // 发送mq异步处理
        rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.SYNC_CODE_ROUTING_KEY, newMsg, 3*60 *1000);
      }
    } catch (Exception e) {
      log.error("同步场次码延迟队列异常: {}", e.getMessage());
    } finally {
      // 消费成功，消息会被删除
      channel.basicAck(deliveryTag, false);
    }
  }

  /**
   * 取消订单延迟队列
   */
  @RabbitListener(queues = DelayRabbitMQConfig.CANCEL_ORDER_QUEUE, ackMode = "MANUAL")
  public void receiveCancelOrderMessage(Message message, Channel channel,
                                        @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
    try {
      // 处理队列消息
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.error("取消订单延迟队列消息: {}", msg);
      if(StringUtils.isEmpty(msg)) {
        throw new Exception("消息体错误");
      }
      long orderId = Long.parseLong(msg);
      log.error("取消订单延迟队列消息 orderId: {}", orderId);
      // 查询订单信息
      Order order = orderMapper.selectById(orderId);
      if(order.getStatus() != 1) {
        throw new Exception("订单状态错误");
      }
      // 修改订单状态
      order.setId(orderId);
      order.setStatus(5);
      int rowsAffected = orderMapper.updateStatus(order);
      if (rowsAffected <= 0) {
        throw new Exception("取消订单失败");
      }
      log.error("取消订单延迟队列成功");
    } catch (Exception e) {
      log.error("取消订单延迟队列异常: {}", e.getMessage());
    } finally {
      channel.basicAck(deliveryTag, false);
    }
  }

  /**
   * 发起退款延迟队列
   */
  @RabbitListener(queues = DelayRabbitMQConfig.REFUND_QUEUE, ackMode = "MANUAL")
  public void receiveRefundMessage(Message message, Channel channel,
                                   @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
    try {
      // 处理队列消息
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.error("发起退款延迟队列: {}", msg);
      if(StringUtils.isEmpty(msg)) {
        throw new Exception("消息体错误");
      }
      long orderId = Long.parseLong(msg);
      log.error("发起退款延迟队列 orderId: {}", orderId);
      // 查询订单信息
      Order order = orderMapper.selectById(orderId);
      if(order == null || order.getStatus() == 4) {
        throw new Exception("订单已出票，不能退款");
      }
      // 获取支付句柄
      String appletType = AppletTypeEnum.findByCode(order.getPlatform());
      AppletService appletService = appletServiceFactory.getAppletService(appletType);
      // 扭转订单状态到退款中，并生成退款订单号
      String refundNo = generateOrderSn();
      if(order.getRefundNo() != null) {
        refundNo = order.getRefundNo();
      } else {
        order.setStatus(6);
        order.setRefundNo(refundNo);
        // 调用保存
        int rowsAffected = orderMapper.update(order);
        log.error("发起退款延迟队列 先接口更新数据: {}", rowsAffected > 0);
      }
      // 组装参数
      Map<String, Object> refundData = new HashMap<>(4);
      refundData.put("outTradeNo", order.getOrderSn());
      refundData.put("tradeNo", order.getTradeNo());
      refundData.put("refundNo", refundNo);
      refundData.put("totalAmount", order.getTotalPrice());
      refundData.put("baiduUserId", order.getBaiduUserId());
      // 发送退款请求
      boolean res = appletService.refundOrder(refundData);
      log.error("发起退款延迟队列 退款结果: {}", res);
      // 处理退款结果
      if (res) {
        order.setStatus(7);
        int rowsAffected = orderMapper.update(order);
        log.error("发起退款延迟队列 后接口更新数据: {}", rowsAffected > 0);
      } else {
        log.error("发起退款延迟队列 发起失败保底查询一下退款");
        // 百度走自己的退款回调
        if(order.getPlatform() != AppletTypeEnum.BAIDU.getCode()) {
          // 发送mq异步处理 2分钟后查询退款订单
          rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.REFUND_QUERY_ROUTING_KEY, msg, 2*60 *1000);
        } else {
          // 发送mq异步处理 2分钟后查询退款订单
          rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.REFUND_QUERY_ROUTING_KEY, msg, 2*60 *1000);
        }
      }
    } catch (Exception e) {
      log.error("退款队列异常: {}", e.getMessage());
    } finally {
      channel.basicAck(deliveryTag, false);
    }
  }

  /**
   * 退款订单查询延迟队列
   */
  @RabbitListener(queues = DelayRabbitMQConfig.REFUND_QUERY_QUEUE, ackMode = "MANUAL")
  public void receiveRefundQueryMessage(Message message, Channel channel,
                                        @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
    try {
      // 处理队列消息
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.error("退款订单查询延迟队列: {}", msg);
      if(StringUtils.isEmpty(msg)) {
        throw new Exception("消息体错误");
      }
      long orderId = Long.parseLong(msg);
      log.error("退款订单查询延迟队列 orderId: {}", orderId);
      // 查询订单信息
      Order order = orderMapper.selectById(orderId);
      // 获取支付句柄
      String appletType = AppletTypeEnum.findByCode(order.getPlatform());
      AppletService appletService = appletServiceFactory.getAppletService(appletType);
      // 组装参数
      Map<String, String> queryData = new HashMap<>(4);
      queryData.put("outTradeNo", order.getTradeNo());
      queryData.put("tradeNo", order.getOrderSn());
      queryData.put("refundNo", order.getRefundNo());
      // 发送退款请求
      boolean res = appletService.queryRefund(queryData);
      log.error("退款订单查询延迟队列查询结果: {}", res);
      // 处理退款结果
      if (res) {
        order.setStatus(7);
      } else {
        order.setStatus(8);
      }
      int rowsAffected = orderMapper.update(order);
      if (rowsAffected <= 0) {
        throw new Exception("数据更新失败");
      }
      log.error("退款订单查询延迟队列成功");
    } catch (Exception e) {
      log.error("退款订单查询延迟队列异常: {}", e.getMessage());
    } finally {
      channel.basicAck(deliveryTag, false);
    }
  }

}