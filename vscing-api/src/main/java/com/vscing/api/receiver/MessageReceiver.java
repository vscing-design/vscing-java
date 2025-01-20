package com.vscing.api.receiver;

import com.vscing.mq.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

  @RabbitListener(queues = RabbitMQConfig.SYNC_CODE_QUEUE)
  public void receiveSyncCodeMessage(Object message) throws Exception {
    try {
      // 处理同步场次码消息
      System.out.println(" [x] Received sync code message: " + message);
      // 模拟处理失败的情况
//      if (/* some condition */) {
//        throw new Exception("Failed to process sync code message");
//      }
    } catch (Exception e) {
      System.err.println(" [x] Failed to process sync code message: " + e.getMessage());
      // 抛出异常后，RabbitMQ 不会确认消息，它会被重新入队或发送到死信队列
      throw e;
    }
  }

  @RabbitListener(queues = RabbitMQConfig.CANCEL_ORDER_QUEUE)
  public void receiveCancelOrderMessage(Object message) throws Exception {
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
  public void receiveDlxMessage(Object message) throws Exception {
    // 处理死信队列中的消息，可以尝试重新处理或记录日志等
    System.out.println(" [x] Received dead letter message: " + message);
    // 再次尝试处理消息，或者根据业务逻辑决定下一步操作
  }
}