package com.vscing.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * DelayRabbitMQConfig 延迟队列配置
 *
 * @author vscing
 * @date 2025/3/8 00:17
 */
@Slf4j
@Configuration
public class DelayRabbitMQConfig {

  /**
   * 延迟交换机名称
   */
  public static final String DELAYED_EXCHANGE_NAME = "delayed_exchange";

  /**
   * 同步场次码相关配置
   */
  public static final String SYNC_CODE_QUEUE = "sync_code_queue";
  public static final String SYNC_CODE_ROUTING_KEY = "sync_code_routing_key";

  /**
   * 取消订单相关配置
   */
  public static final String CANCEL_ORDER_QUEUE = "cancel_order_queue";
  public static final String CANCEL_ORDER_ROUTING_KEY = "cancel_order_routing_key";

  /**
   * 退款相关配置
   */
  public static final String REFUND_QUEUE = "refund_queue";
  public static final String REFUND_ROUTING_KEY = "refund_routing_key";

  /**
   * 退款订单查询相关配置
   */
  public static final String REFUND_QUERY_QUEUE = "refund_query_queue";
  public static final String REFUND_QUERY_ROUTING_KEY = "refund_query_routing_key";

  /**
   * 同步会员商品订单信息
  */
  public static final String SYNC_VIP_ORDER_QUEUE = "sync_vip_order_queue";
  public static final String SYNC_VIP_ORDER_ROUTING_KEY = "sync_vip_order_routing_key";

  /**
   * 订单异步通知
   */
  public static final String ORDER_NOTIFY_QUEUE = "order.notify.queue";
  public static final String ORDER_NOTIFY_ROUTING_KEY = "order.notify.routing.key";

  /**
   * 创建延迟交换机。
   */
  @Bean
  public CustomExchange delayedExchange() {
    Map<String, Object> args = new HashMap<>();
    args.put("x-delayed-type", "direct");
    return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, args);
  }

  /**
   * 创建同步场次码延迟队列
   * TODO: 可指定死信队列。常见场景：消息过期、队列长度限制、消息被拒绝、处理失败等。
   * TODO: 也可使用 new Queue(SYNC_CODE_QUEUE, true, false, false, args); 创建持久化队列。
   */
  @Bean
  public Queue syncCodeQueue() {
    Map<String, Object> args = new HashMap<>(2);
    // 指定死信交换机
    args.put("x-dead-letter-exchange", DeadRabbitMQConfig.DEAD_EXCHANGE_NAME);
    // 指定死信路由键
    args.put("x-dead-letter-routing-key", DeadRabbitMQConfig.DEAD_ROUTING_KEY);
    // 设置消息TTL为60秒
//    args.put("x-message-ttl", 60000);
    return QueueBuilder.durable(SYNC_CODE_QUEUE).withArguments(args).build();
  }

  /**
   * 绑定同步场次码队列到延迟交换机
  */
  @Bean
  public Binding bindingSyncCodeQueueToDelayedExchange(Queue syncCodeQueue) {
    return BindingBuilder.bind(syncCodeQueue).to(delayedExchange()).with(SYNC_CODE_ROUTING_KEY).noargs();
  }

  /**
   * 创建取消订单延迟队列
   */
  @Bean
  public Queue cancelOrderQueue() {
    return QueueBuilder.durable(CANCEL_ORDER_QUEUE).build();
  }

  /**
   * 绑定取消订单队列到延迟交换机
  */
  @Bean
  public Binding bindingCancelOrderQueueToDelayedExchange(Queue cancelOrderQueue) {
    return BindingBuilder.bind(cancelOrderQueue).to(delayedExchange()).with(CANCEL_ORDER_ROUTING_KEY).noargs();
  }

  /**
   * 创建退款延迟队列
   */
  @Bean
  public Queue refundQueue() {
    return QueueBuilder.durable(REFUND_QUEUE).build();
  }

  /**
   * 绑定退款队列到延迟交换机
  */
  @Bean
  public Binding bindingRefundQueueToDelayedExchange(Queue refundQueue) {
    return BindingBuilder.bind(refundQueue).to(delayedExchange()).with(REFUND_ROUTING_KEY).noargs();
  }

  /**
   * 创建退款订单查询延迟队列
   */
  @Bean
  public Queue refundQueryQueue() {
    return QueueBuilder.durable(REFUND_QUERY_QUEUE).build();
  }

  /**
   * 绑定退款订单查询队列到延迟交换机
  */
  @Bean
  public Binding bindingRefundQueryQueueToDelayedExchange(Queue refundQueryQueue) {
    return BindingBuilder.bind(refundQueryQueue).to(delayedExchange()).with(REFUND_QUERY_ROUTING_KEY).noargs();
  }

  /**
   * 创建同步会员商品订单信息队列
   */
  @Bean
  public Queue syncVipOrderQueue() {
    return QueueBuilder.durable(SYNC_VIP_ORDER_QUEUE).build();
  }

  /**
   * 绑定同步会员商品订单信息队列到延迟交换机
   */
  @Bean
  public Binding bindingSyncVipOrderQueueToDelayedExchange(Queue syncVipOrderQueue) {
    return BindingBuilder.bind(syncVipOrderQueue).to(delayedExchange()).with(SYNC_VIP_ORDER_ROUTING_KEY).noargs();
  }

  /**
   * 订单异步通知队列
   */
  @Bean
  public Queue orderNotifyQueue() {
    return QueueBuilder.durable(ORDER_NOTIFY_QUEUE).build();
  }

  /**
   * 绑定订单异步通知队列到延迟交换机
   */
  @Bean
  public Binding bindingOrderNotifyQueueToDelayedExchange(Queue orderNotifyQueue) {
    return BindingBuilder.bind(orderNotifyQueue).to(delayedExchange()).with(ORDER_NOTIFY_ROUTING_KEY).noargs();
  }

}
