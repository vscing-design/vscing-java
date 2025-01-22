package com.vscing.mq.service.impl;

import com.vscing.mq.service.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.vscing.mq.config.RabbitMQConfig.DELAYED_EXCHANGE_NAME;

/**
 * RabbitMQServiceImpl
 *
 * @author vscing
 * @date 2025/1/23 00:27
 */
@Slf4j
@Service
public class RabbitMQServiceImpl implements RabbitMQService {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Override
  public void sendDelayedMessage(String routingKey, Object message, int delayMilliseconds) {
    rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, routingKey, message, msg -> {
      msg.getMessageProperties().setHeader("x-delay", delayMilliseconds);
      return msg;
    });
    log.info("已发送延迟消息: routingKey={}, message={}", routingKey, message);
  }

}
