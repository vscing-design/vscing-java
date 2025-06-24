package com.vscing.api.receiver;

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
import com.vscing.common.utils.OrderUtils;
import com.vscing.common.utils.StringUtils;
import com.vscing.model.entity.Coupon;
import com.vscing.model.entity.Order;
import com.vscing.model.entity.VipOrder;
import com.vscing.model.enums.AppletTypeEnum;
import com.vscing.model.http.HttpOrder;
import com.vscing.model.mapper.CouponMapper;
import com.vscing.model.mapper.OrderMapper;
import com.vscing.model.mapper.VipOrderMapper;
import com.vscing.model.mq.OrderNotifyMq;
import com.vscing.model.mq.SyncCodeMq;
import com.vscing.model.platform.QueryOrderTicket;
import com.vscing.model.platform.QueryOrderTicketDto;
import com.vscing.model.platform.QueryVipOrderTicket;
import com.vscing.mq.config.DelayRabbitMQConfig;
import com.vscing.mq.service.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
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

  private static final OkHttpClient client = new OkHttpClient();

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

  @Autowired
  private CouponMapper couponMapper;

  @Autowired
  private VipOrderMapper vipOrderMapper;

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
      log.error("同步场次码延迟队列数据更新前: {}", httpOrder);
      if(httpOrder == null || !ORDER_STATUS_GENERATE_SUCCESS.equals(httpOrder.getOrderStatus())){
        tag = 1;
      }
      // 如果成功了，就执行更新数据
      if (tag == 0) {
        // 更新数据
        boolean result = orderService.supplierOrder(httpOrder);
        log.error("同步场次码延迟队列数据更新结果：{}", result);
      }
      // 判断是否需要重试
      if(tag == 1 && syncCodeMq.getNum() >= 20) {
        // 发送mq异步处理 退款
        rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.REFUND_ROUTING_KEY, syncCodeMq.getOrderId().toString(), 2*60*1000);
      } else if(tag == 1) {
        syncCodeMq.setNum(syncCodeMq.getNum() + 1);
        String newMsg = JsonUtils.toJsonString(syncCodeMq);
        // 发送mq异步处理
        rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.SYNC_CODE_ROUTING_KEY, newMsg, 3*60*1000);
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
      // 查看是否有优惠券
      if(order.getCouponId() != null) {
        Coupon coupon = new Coupon();
        coupon.setId(order.getCouponId());
        coupon.setStatus(1);
        coupon.setVerifyAt(null);
        rowsAffected = couponMapper.updateStatus(coupon);
        if (rowsAffected <= 0) {
          throw new Exception("退还优惠券失败");
        }
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
      String refundNo = OrderUtils.generateOrderSn("HY-TD", 1);
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
          rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.REFUND_QUERY_ROUTING_KEY, msg, 2*60*1000);
        } else {
          // 发送mq异步处理 2分钟后查询退款订单
          rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.REFUND_QUERY_ROUTING_KEY, msg, 2*60*1000);
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
      // 标记
      int rowsAffected = 0;
      // 处理退款结果
      if (res) {
        order.setStatus(7);
        // 查看是否有优惠券
        if(order.getCouponId() != null) {
          Coupon coupon = new Coupon();
          coupon.setId(order.getCouponId());
          coupon.setStatus(1);
          coupon.setVerifyAt(null);
          rowsAffected = couponMapper.updateStatus(coupon);
          if (rowsAffected <= 0) {
            throw new Exception("退还优惠券失败");
          }
        }
      } else {
        order.setStatus(8);
      }
      rowsAffected = orderMapper.update(order);
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

  /**
   * 会员商品订单查询延迟队列
   */
  @RabbitListener(queues = DelayRabbitMQConfig.SYNC_VIP_ORDER_QUEUE, ackMode = "MANUAL")
  public void syncVipOrderMessage(Message message, Channel channel,
                                  @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
    try {
      // 处理队列消息
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.error("会员商品订单查询延迟队列: {}", msg);
      if(StringUtils.isEmpty(msg)) {
        throw new Exception("消息体错误");
      }
      long orderId = Long.parseLong(msg);
      log.error("会员商品订单查询延迟队列 orderId: {}", orderId);
      // 查询订单信息
      VipOrder vipOrder = vipOrderMapper.selectById(orderId);
      if (vipOrder == null || vipOrder.getStatus() > 2) {
        throw new Exception("订单信息错误");
      }
      // 准备请求参数
      Map<String, String> params = new HashMap<>();
      params.put("usorderno", vipOrder.getOrderSn());
      SupplierService supplierService = supplierServiceFactory.getSupplierService("kky");
      // 发送请求并获取响应
      String responseBody = supplierService.sendRequest("/dockapiv3/order/get", params);
      // 将 JSON 字符串解析为 JsonNode 对象
      ObjectMapper objectMapper = JsonUtils.getObjectMapper();
      // 解析 JSON 数据到 Map
      Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
      int code = (int) responseMap.get("code");
      Map<String, Object> data = (Map<String, Object>) responseMap.get("data");
      // 判断数据
      if (code != 1 || data == null) {
        log.info("vip同步商品订单详情结果异常: {}", responseBody);
        // 发送mq异步处理 2分钟后查询退款订单
        rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.SYNC_VIP_ORDER_ROUTING_KEY, vipOrder.getId().toString(), 2*60*1000);
        return;
      }
      // 标记
      int rowsAffected = 0;
      Integer status = objectMapper.convertValue(data.get("status"), Integer.class);
      if (status == 3) {
        vipOrder.setStatus(2);
      } else if (status == 4) {
        vipOrder.setStatus(4);
      } else if (status == 5) {
        vipOrder.setStatus(3);
      }
      vipOrder.setSupplierOrderSn(objectMapper.convertValue(data.get("orderno"), String.class));
      vipOrder.setRefundMoney(objectMapper.convertValue(data.get("refundmoney"), BigDecimal.class));
      vipOrder.setRefundStatus(objectMapper.convertValue(data.get("refundstatus"), Integer.class));
      vipOrder.setCardList(objectMapper.convertValue(data.get("cardlist"), List.class).toString());
      vipOrder.setReceipt(objectMapper.convertValue(data.get("receipt"), String.class));
      vipOrder.setRefundReceipt(objectMapper.convertValue(data.get("refundreceipt"), String.class));
      rowsAffected = vipOrderMapper.update(vipOrder);
      if (rowsAffected <= 0) {
        log.info("vip同步商品订单详情数据更新失败");
        // 发送mq异步处理 2分钟后查询退款订单
        rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.SYNC_VIP_ORDER_ROUTING_KEY, vipOrder.getId().toString(), 2*60*1000);
        return;
      }
      log.error("vip同步商品订单详情延迟队列成功");
    } catch (Exception e) {
      log.error("vip同步商品订单详情延迟队列异常: {}", e.getMessage());
    } finally {
      channel.basicAck(deliveryTag, false);
    }
  }

  /**
   * 订单异步通知延迟队列
   */
  @RabbitListener(queues = DelayRabbitMQConfig.ORDER_NOTIFY_QUEUE, ackMode = "MANUAL")
  public void orderNotifyMessage(Message message, Channel channel,
                                 @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
    OrderNotifyMq orderNotifyMq = null;
    try {
      // 处理队列消息
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.error("订单异步通知延迟队列: {}", msg);
      if (StringUtils.isEmpty(msg)) {
        throw new Exception("消息体错误");
      }
      orderNotifyMq = JsonUtils.parseObject(msg, OrderNotifyMq.class);
      if (orderNotifyMq == null) {
        throw new Exception("解析消息体错误");
      }
      // 判断url是否正常 最大通知次数
      if (orderNotifyMq.getUrl() == null || orderNotifyMq.getUrl().trim().isEmpty() || !orderNotifyMq.getUrl().matches("^(?i)(http|https)://.*")) {
        return;
      }
      if (orderNotifyMq.getNum() > 5) {
        return;
      }
      // 查询订单信息
      String jsonBody = "";
      ObjectMapper objectMapper = JsonUtils.getObjectMapper();
      QueryOrderTicketDto record = new QueryOrderTicketDto();
      record.setOrderNo(orderNotifyMq.getOrderNo());
      if(orderNotifyMq.getOrderType() == 1) {
        QueryOrderTicket queryOrderTicket = orderMapper.getPlatformInfo(record);
        jsonBody = objectMapper.writeValueAsString(queryOrderTicket);
      } else {
        QueryVipOrderTicket queryVipOrderTicket = vipOrderMapper.getPlatformInfo(record);
        jsonBody = objectMapper.writeValueAsString(queryVipOrderTicket);
      }
      // 发送请求
      MediaType JSON = MediaType.get("application/json; charset=utf-8");
      RequestBody body = RequestBody.create(jsonBody, JSON);
      Request.Builder requestBuilder = new Request.Builder()
          .url(orderNotifyMq.getUrl())
          .post(body);
      Request request = requestBuilder.build();
      // 请求异常处理
      try (Response response = client.newCall(request).execute()) {
        if (!response.isSuccessful()) {
          throw new IOException("Unexpected code " + response);
        }
        // 处理退款结果
        log.error("订单异步通知延迟队列成功: {}", response.body().string());
        if(!response.body().string().equals("ok")) {
          throw new Exception("响应异常");
        }
      } catch (Exception e) {
        throw new Exception(e.getMessage());
      }
    } catch (Exception e) {
      log.error("订单异步通知延迟队列异常: {}", e.getMessage());
      if(orderNotifyMq != null) {
        // 处理队列消息
        int num = orderNotifyMq.getNum() + 1;
        orderNotifyMq.setNum(num);
        String newMsg = JsonUtils.toJsonString(orderNotifyMq);
        rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.ORDER_NOTIFY_ROUTING_KEY, newMsg, (long) num*5*60*1000);
      }
    } finally {
      channel.basicAck(deliveryTag, false);
    }
  }

}