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
 * FanoutRabbitMQConfig
 *
 * @author vscing
 * @date 2025/1/21 00:21
 */
@Slf4j
@Configuration
public class FanoutRabbitMQConfig {

    /**
     * 延迟交换机名称
    */
    public static final String FANOUT_EXCHANGE_NAME = "fanout_exchange";

    /**
     * 邀请新人相关配置
    */
    public static final String INVITE_QUEUE = "invite_queue";
    public static final String INVITE_ROUTING_KEY = "invite_routing_key";

    /**
     * 订单返利相关配置
     */
    public static final String REBATE_QUEUE = "rebate_queue";
    public static final String REBATE_ROUTING_KEY = "rebate_routing_key";

    /**
     * 转账相关配置
     */
    public static final String TRANSFER_QUEUE = "transfer_queue";
    public static final String TRANSFER_ROUTING_KEY = "transfer_routing_key";

    /**
     * 订单数据同步配置
    */
    public static final String SYNC_ORDER_QUEUE = "sync_order_queue";
    public static final String SYNC_ORDER_ROUTING_KEY = "sync_order_routing_key";

    /**
     * 延迟交换机
     */
    @Bean
    public DirectExchange fanoutExchange() {
        return new DirectExchange(FANOUT_EXCHANGE_NAME);
    }

    /**
     * 邀请新人队列
    */
    @Bean
    public Queue inviteQueue() {
        return QueueBuilder.durable(INVITE_QUEUE).build();
    }

    /**
     * 绑定邀请新人队列到延迟交换机
    */
    @Bean
    public Binding inviteBinding(Queue inviteQueue) {
        return BindingBuilder.bind(inviteQueue).to(fanoutExchange()).with(INVITE_ROUTING_KEY);
    }

    /**
     * 订单返利队列
    */
    @Bean
    public Queue rebateQueue() {
        return QueueBuilder.durable(REBATE_QUEUE).build();
    }

    /**
     * 绑定订单返利队列到延迟交换机
    */
    @Bean
    public Binding rebateBinding(Queue rebateQueue) {
        return BindingBuilder.bind(rebateQueue).to(fanoutExchange()).with(REBATE_ROUTING_KEY);
    }

    /**
     * 转账队列
     */
    @Bean
    public Queue transferQueue() {
        return QueueBuilder.durable(TRANSFER_QUEUE).build();
    }

    /**
     * 绑定转账队列到延迟交换机
     */
    @Bean
    public Binding transferBinding(Queue transferQueue) {
        return BindingBuilder.bind(transferQueue).to(fanoutExchange()).with(TRANSFER_ROUTING_KEY);
    }

    /**
     * 订单数据同步队列
     */
    @Bean
    public Queue syncOrderQueue() {
        return QueueBuilder.durable(SYNC_ORDER_QUEUE).build();
    }

    /**
     * 绑定订单数据同步队列到延迟交换机
     */
    @Bean
    public Binding syncOrderBinding(Queue syncOrderQueue) {
        return BindingBuilder.bind(syncOrderQueue).to(fanoutExchange()).with(SYNC_ORDER_ROUTING_KEY);
    }

}

