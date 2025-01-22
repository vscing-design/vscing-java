package com.vscing.mq.service;

/**
 * RabbitMQService
 *
 * @author vscing
 * @date 2025/1/23 00:26
 */
public interface RabbitMQService {

  /**
   * 发送延迟队列
  */
  void sendDelayedMessage(String routingKey, Object message, int delayMilliseconds);

}
