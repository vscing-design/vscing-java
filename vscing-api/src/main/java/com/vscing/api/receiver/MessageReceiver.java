package com.vscing.api.receiver;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.api.service.OrderService;
import com.vscing.common.api.ResultCode;
import com.vscing.common.service.RedisService;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.common.service.supplier.SupplierService;
import com.vscing.common.service.supplier.SupplierServiceFactory;
import com.vscing.model.entity.Order;
import com.vscing.model.enums.PaymentTypeEnum;
import com.vscing.model.http.HttpOrder;
import com.vscing.model.mapper.OrderMapper;
import com.vscing.mq.config.RabbitMQConfig;
import com.vscing.mq.service.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class MessageReceiver {

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
   * 使用的自动应答，所以抛出异常就一直消费，死循环了。
   * 正确应该是异常之后还是当正常消费，再执行一个消息。
  */
  @RabbitListener(queues = RabbitMQConfig.SYNC_CODE_QUEUE)
  public void receiveSyncCodeMessage(Message message) {
    int tag = 0;
    // 处理同步场次码消息
    String msg = new String(message.getBody(), StandardCharsets.UTF_8);
    log.info("场次队列消息: {}" + msg);
    Long orderId = Long.parseLong(msg);
    try {
      // 查询订单信息
      Order order = orderMapper.selectById(orderId);
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
        log.error("code: {}, message: {}", code, apiMessage);
        throw new Exception(apiMessage);
      }
      Object data = responseMap.get("data");
      if(data == null){
        tag = 1;
        throw new Exception("未获取到取票数据");
      }
      HttpOrder httpOrder = objectMapper.convertValue(data, HttpOrder.class);
      if(httpOrder == null || !ORDER_STATUS_GENERATE_SUCCESS.equals(httpOrder.getOrderStatus())){
        tag = 1;
        throw new Exception("未获取到取票数据");
      }
      boolean res = orderService.supplierOrder(httpOrder);
      if(!res) {
        throw new Exception("同步出票中订单失败");
      }
      log.error("同步出票中订单处理成功");
    } catch (Exception e) {
      log.error("场次队列异常: " + e.getMessage());
//      throw e;
    }
    if(tag == 1) {
      // 发送mq异步处理
      rabbitMQService.sendDelayedMessage(RabbitMQConfig.SYNC_CODE_ROUTING_KEY, orderId.toString(), 3*60 *1000);
    }
  }

  /**
   * 取消订单队列
  */
  @RabbitListener(queues = RabbitMQConfig.CANCEL_ORDER_QUEUE)
  public void receiveCancelOrderMessage(Message message) {
    try {
      // 处理同步场次码消息
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.info("取消订单队列消息: {}" + msg);
      Long orderId = Long.parseLong(msg);
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
      log.info("取消订单成功");
    } catch (Exception e) {
      log.error("取消订单队列异常: " + e.getMessage());
    }
  }

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
   * 出票失败 或 出票结果迟迟不响应 走退款流程
   */
  @RabbitListener(queues = RabbitMQConfig.REFUND_QUEUE)
  public void receiveRefundMessage(Message message) {
    try {
      // 获取订单ID
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.info("退款队列消息: {}" + msg);
      Long orderId = Long.parseLong(msg);
      // 查询订单信息
      Order order = orderMapper.selectById(orderId);
      // 获取支付句柄
      String paymentType = PaymentTypeEnum.findByCode(order.getPlatform());
      AppletService appletService = appletServiceFactory.getAppletService(paymentType);
      // 扭转订单状态到退款中，并生成退款订单号
      String refundNo = generateOrderSn();
      order.setStatus(6);
      order.setRefundNo(refundNo);
      // 调用保存
      orderMapper.update(order);
      // 组装参数
      Map<String, Object> refundData = new HashMap<>(4);
      refundData.put("outTradeNo", order.getTradeNo());
      refundData.put("tradeNo", order.getOrderSn());
      refundData.put("refundNo", refundNo);
      refundData.put("totalAmount", order.getTotalPrice());
      // 发送退款请求
      boolean res = appletService.refundOrder(refundData);
      log.info("退款结果: {}", res);
      // 处理退款结果
      if (res) {
        order.setStatus(7);
        orderMapper.update(order);
      } else {
        // 发送mq异步处理 2分钟后查询退款订单
        rabbitMQService.sendDelayedMessage(RabbitMQConfig.REFUND_QUERY_ROUTING_KEY, order.getId().toString(), 2*60 *1000);
      }
    } catch (Exception e) {
      log.error("退款队列异常: {}", e.getMessage());
    }
  }

  /**
   * 退款订单查询
   */
  @RabbitListener(queues = RabbitMQConfig.REFUND_QUERY_QUEUE)
  public void receiveRefundQueryMessage(Message message) {
    try {
      // 获取订单ID
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.info("退款订单查询队列消息: {}" + msg);
      Long orderId = Long.parseLong(msg);
      // 查询订单信息
      Order order = orderMapper.selectById(orderId);
      // 获取支付句柄
      String paymentType = PaymentTypeEnum.findByCode(order.getPlatform());
      AppletService appletService = appletServiceFactory.getAppletService(paymentType);
      // 组装参数
      Map<String, String> queryData = new HashMap<>(4);
      queryData.put("outTradeNo", order.getTradeNo());
      queryData.put("tradeNo", order.getOrderSn());
      queryData.put("refundNo", order.getRefundNo());
      // 发送退款请求
      boolean res = appletService.queryRefund(queryData);
      log.info("退款订单查询结果: {}", res);
      // 处理退款结果
      if (res) {
        order.setStatus(7);
      } else {
        order.setStatus(8);
      }
      orderMapper.update(order);
    } catch (Exception e) {
      log.error("退款队列异常: {}", e.getMessage());
    }
  }

  @RabbitListener(queues = RabbitMQConfig.DLX_QUEUE_NAME)
  public void receiveDlxMessage(Message message) throws Exception {
    // 获取 x-death 头信息
    int retryCount = 0;
    if (redisService.hasKey("retryCount")) {
      retryCount = (Integer) redisService.get("retryCount");
    }
    System.out.println(" [x] Retry count: " + retryCount);
    // 处理死信队列中的消息，可以尝试重新处理或记录日志等
    System.out.println(" [x] Received dead letter message: " + message);
    // 再次尝试处理消息，或者根据业务逻辑决定下一步操作
  }
}