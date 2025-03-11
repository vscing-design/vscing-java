package com.vscing.api.receiver;

import com.rabbitmq.client.Channel;
import com.vscing.api.service.UserService;
import com.vscing.common.utils.JsonUtils;
import com.vscing.common.utils.StringUtils;
import com.vscing.model.mq.InviteMq;
import com.vscing.model.mq.RebateMq;
import com.vscing.mq.config.FanoutRabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * FanoutMessageReceiver
 *
 * @author vscing
 * @date 2025/3/8 18:25
 */
@Slf4j
@Component
public class FanoutMessageReceiver {

  @Autowired
  private UserService userService;

  /**
   * 邀请新人队列
   */
  @RabbitListener(queues = FanoutRabbitMQConfig.INVITE_QUEUE, ackMode = "MANUAL")
  public void receiveInviteMessage(Message message, Channel channel,
                                     @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
    try {
      // 处理队列消息
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.error("邀请新人队列消息: {}", msg);
      if(StringUtils.isEmpty(msg)) {
        throw new Exception("消息体错误");
      }
      InviteMq inviteMq = JsonUtils.parseObject(msg, InviteMq.class);
      if(inviteMq == null) {
        throw new Exception("解析消息体错误");
      }
      log.error("邀请新人队列消息 userId: {}, inviteUserId: {}", inviteMq.getUserId(), inviteMq.getInviteUserId());
      userService.userInvite(inviteMq);
      log.error("邀请新人队列消息处理成功");
    } catch (Exception e) {
      log.error("邀请新人队列消息处理失败: {}", e.getMessage());
    } finally {
      channel.basicAck(deliveryTag, false);
    }
  }

  /**
   * 订单返利队列
   */
  @RabbitListener(queues = FanoutRabbitMQConfig.REBATE_QUEUE, ackMode = "MANUAL")
  public void receiveRebateMessage(Message message, Channel channel,
                                     @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
    try {
      // 处理队列消息
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.error("订单返利队列消息: {}", msg);
      if(StringUtils.isEmpty(msg)) {
        throw new Exception("消息体错误");
      }
      RebateMq rebateMq = JsonUtils.parseObject(msg, RebateMq.class);
      if(rebateMq == null) {
        throw new Exception("解析消息体错误");
      }
      log.error("订单返利队列消息 userId: {}, orderId: {}", rebateMq.getUserId(), rebateMq.getOrderId());
      userService.userRebate(rebateMq);
    } catch (Exception e) {
      log.error("订单返利队列消息处理失败: {}", e.getMessage());
    } finally {
      channel.basicAck(deliveryTag, false);
    }
  }

}
