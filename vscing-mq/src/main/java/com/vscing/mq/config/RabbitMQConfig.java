package com.vscing.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQConfig
 *
 * @author vscing
 * @date 2025/1/21 00:21
 */
@Slf4j
@Configuration
public class RabbitMQConfig {

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
     * 死信交换机和队列配置
    */
    public static final String DLX_EXCHANGE_NAME = "dlx_exchange";
    public static final String DLX_QUEUE_NAME = "dlx_queue";
    public static final String DLX_ROUTING_KEY = "dlx_routing_key";

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
     * 创建同步场次码队列，并绑定到延迟交换机。
     */
    @Bean
    public Queue syncCodeQueue() {
        Map<String, Object> args = new HashMap<>(2);
        // 指定死信交换机
        args.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);
        // 指定死信路由键
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        return QueueBuilder.durable(SYNC_CODE_QUEUE).withArguments(args).build();
    }

    @Bean
    public Binding bindingSyncCodeQueueToDelayedExchange(Queue syncCodeQueue) {
        return BindingBuilder.bind(syncCodeQueue).to(delayedExchange()).with(SYNC_CODE_ROUTING_KEY).noargs();
    }

    /**
     * 创建取消订单队列，并绑定到延迟交换机。
     */
    @Bean
    public Queue cancelOrderQueue() {
        Map<String, Object> args = new HashMap<>(2);
        // 指定死信交换机
        args.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);
        // 指定死信路由键
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        return QueueBuilder.durable(CANCEL_ORDER_QUEUE).withArguments(args).build();
    }

    @Bean
    public Binding bindingCancelOrderQueueToDelayedExchange(Queue cancelOrderQueue) {
        return BindingBuilder.bind(cancelOrderQueue).to(delayedExchange()).with(CANCEL_ORDER_ROUTING_KEY).noargs();
    }

    /**
     * 创建退款队列，并绑定到延迟交换机。
     */
    @Bean
    public Queue refundQueue() {
        Map<String, Object> args = new HashMap<>(2);
        // 指定死信交换机
        args.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);
        // 指定死信路由键
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        return QueueBuilder.durable(REFUND_QUEUE).withArguments(args).build();
    }

    @Bean
    public Binding bindingRefundQueueToDelayedExchange(Queue refundQueue) {
        return BindingBuilder.bind(refundQueue).to(delayedExchange()).with(REFUND_ROUTING_KEY).noargs();
    }

    /**
     * 创建退款订单查询队列，并绑定到延迟交换机。
     */
    @Bean
    public Queue refundQueryQueue() {
        Map<String, Object> args = new HashMap<>(2);
        // 指定死信交换机
        args.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);
        // 指定死信路由键
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        return QueueBuilder.durable(REFUND_QUERY_QUEUE).withArguments(args).build();
    }

    @Bean
    public Binding bindingRefundQueryQueueToDelayedExchange(Queue refundQueryQueue) {
        return BindingBuilder.bind(refundQueryQueue).to(delayedExchange()).with(REFUND_QUERY_ROUTING_KEY).noargs();
    }

    /**
     * 创建死信交换机和队列。
     */
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE_NAME);
    }

    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.durable(DLX_QUEUE_NAME).build();
    }

    @Bean
    public Binding bindingDlxQueueToDlxExchange(Queue dlxQueue, DirectExchange dlxExchange) {
        return BindingBuilder.bind(dlxQueue).to(dlxExchange).with(DLX_ROUTING_KEY);
    }
}

