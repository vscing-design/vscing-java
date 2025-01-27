package com.vscing.api.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.api.service.OrderService;
import com.vscing.common.api.ResultCode;
import com.vscing.common.service.RedisService;
import com.vscing.common.service.supplier.SupplierService;
import com.vscing.common.service.supplier.SupplierServiceFactory;
import com.vscing.model.entity.Order;
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
    log.error("场次队列消息: {}" + msg);
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
      rabbitMQService.sendDelayedMessage(RabbitMQConfig.SYNC_CODE_ROUTING_KEY, orderId.toString(), 2*60 *1000);
    }
  }

  @RabbitListener(queues = RabbitMQConfig.CANCEL_ORDER_QUEUE)
  public void receiveCancelOrderMessage(Message message) {
    try {
      // 处理同步场次码消息
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.info("取消订单队列消息: {}" + msg);
      Long orderId = Long.parseLong(msg);
      log.info("orderId: {}", orderId);
      // 修改订单状态
      Order order = new Order();
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