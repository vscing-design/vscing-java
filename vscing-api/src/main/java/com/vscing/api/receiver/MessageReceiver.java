package com.vscing.api.receiver;

import com.rabbitmq.client.Channel;
import com.vscing.common.service.RedisService;
import com.vscing.common.utils.JsonUtils;
import com.vscing.mq.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MessageReceiver {

  @Autowired
  private RedisService redisService;

  /**
   * 使用的自动应答，所以抛出异常就一直消费，死循环了。
   * 正确应该是异常之后还是当正常消费，再执行一个消息。
  */
  @RabbitListener(queues = RabbitMQConfig.SYNC_CODE_QUEUE)
  public void receiveSyncCodeMessage(Message message, Channel channel) throws Exception {
    try {
      // 获取 x-death 头信息
      List<Map<String, Object>> xDeathHeaders = (List<Map<String, Object>>) message.getMessageProperties().getHeaders().get("x-death");
      int retryCount = 0;
      if (xDeathHeaders != null && !xDeathHeaders.isEmpty()) {
        Map<String, Object> lastDeathInfo = xDeathHeaders.get(xDeathHeaders.size() - 1);
        retryCount = (Integer) lastDeathInfo.getOrDefault("count", 0);
      }
      System.out.println(" [x] Retry count1: " + retryCount);
      if (redisService.hasKey("retryCount")) {
        retryCount = (Integer) redisService.get("retryCount");
      }
      System.out.println(" [x] Retry count2: " + retryCount);
      // 处理同步场次码消息
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.info("场次队列消息: {}" + msg);
      Map<String, Object> map = JsonUtils.parseMap(msg);
      if("123".equals(map.get("key")) && retryCount == 0) {
        redisService.set("retryCount", 1);
        throw new Exception("key不对");
      }
      log.info("场次队列处理成功");
//      // 如果处理成功，确认消息
//      if (channel.isOpen()) {
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        log.info("场次队列处理成功");
//      } else {
//        log.error("Channel is not open to acknowledge the message.");
//      }
    } catch (Exception e) {
      log.error("场次队列异常: " + e.getMessage());
      throw e;

//      // 检查通道是否打开
//      if (channel != null && channel.isOpen()) {
//        try {
//          // 根据业务逻辑决定是否重新加入队列
//          channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//          // requeue为true表示重新入队
//        } catch (Exception nackEx) {
//          log.error("Failed to nack message: " + nackEx.getMessage());
//        }
//      } else {
//        log.error("Channel is not open to negatively acknowledge the message.");
//
//        // 使用 RabbitTemplate 尝试确认消息
//        // 注意：这种方式不推荐，因为它绕过了正常的确认流程
//        // rabbitTemplate.execute(c -> c.basicAck(message.getMessageProperties().getDeliveryTag(), false));
//      }
    }
  }

  @RabbitListener(queues = RabbitMQConfig.CANCEL_ORDER_QUEUE)
  public void receiveCancelOrderMessage(Message message) throws Exception {
    try {
      // 处理取消订单消息
      System.out.println(" [x] Received cancel order message: " + message);
      // 模拟处理失败的情况
//      if (/* some condition */) {
//        throw new Exception("Failed to process cancel order message");
//      }
    } catch (Exception e) {
      System.err.println(" [x] Failed to process cancel order message: " + e.getMessage());
      // 抛出异常后，RabbitMQ 不会确认消息，它会被重新入队或发送到死信队列
      throw e;
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