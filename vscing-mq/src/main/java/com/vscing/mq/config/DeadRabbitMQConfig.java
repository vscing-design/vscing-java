package com.vscing.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DeadRabbitMQConfig 死信队列配置
 *
 * @author vscing
 * @date 2025/3/8 18:06
 */
@Slf4j
@Configuration
public class DeadRabbitMQConfig {

  /**
   * 死信交换机和队列配置
   */
  public static final String DEAD_EXCHANGE_NAME = "dead_exchange";
  public static final String DEAD_QUEUE = "dead_queue";
  public static final String DEAD_ROUTING_KEY = "dead_routing_key";

  /**
   * 创建死信交换机
   */
  @Bean
  public DirectExchange deadExchange() {
    return new DirectExchange(DEAD_EXCHANGE_NAME);
  }

  /**
   * 创建死信队列
  */
  @Bean
  public Queue deadQueue() {
    return QueueBuilder.durable(DEAD_QUEUE).build();
  }

  /**
   * 绑定死信队列到死信交换机
  */
  @Bean
  public Binding deadBinding(Queue deadQueue) {
    return BindingBuilder.bind(deadQueue).to(deadExchange()).with(DEAD_ROUTING_KEY);
  }

}
