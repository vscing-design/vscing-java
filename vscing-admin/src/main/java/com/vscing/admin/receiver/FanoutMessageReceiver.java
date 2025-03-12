package com.vscing.admin.receiver;

import com.rabbitmq.client.Channel;
import com.vscing.admin.service.UserWithdrawService;
import com.vscing.common.utils.JsonUtils;
import com.vscing.common.utils.StringUtils;
import com.vscing.model.mq.TransferMq;
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
  private UserWithdrawService userWithdrawService;

  /**
   * 转账队列
   */
  @RabbitListener(queues = FanoutRabbitMQConfig.TRANSFER_QUEUE, ackMode = "MANUAL")
  public void receiveInviteMessage(Message message, Channel channel,
                                     @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
    try {
      // 处理队列消息
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.error("转账队列消息: {}", msg);
      if(StringUtils.isEmpty(msg)) {
        throw new Exception("消息体错误");
      }
      TransferMq transferMq = JsonUtils.parseObject(msg, TransferMq.class);
      if(transferMq == null) {
        throw new Exception("解析消息体错误");
      }
      log.error("转账队列消息 userId: {}, platform: {}, amount: {}", transferMq.getUserId(), transferMq.getPlatform(), transferMq.getAmount());
      userWithdrawService.transfer(transferMq);
      log.error("转账队列消息处理成功");
    } catch (Exception e) {
      log.error("转账队列消息处理失败: {}", e.getMessage());
    } finally {
      channel.basicAck(deliveryTag, false);
    }
  }

}
