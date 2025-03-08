package com.vscing.api.receiver;

import com.vscing.common.utils.StringUtils;
import com.vscing.mq.config.DeadRabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * DeadMessageReceiver
 *
 * @author vscing
 * @date 2025/3/9 00:26
 */
@Slf4j
@Component
public class DeadMessageReceiver {

  /**
   * 死信队列
   */
  @RabbitListener(queues = DeadRabbitMQConfig.DEAD_QUEUE)
  public void receiveDlxMessage(Message message) {
    try {
      // 处理队列消息
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.error("死信队列消息: {}", msg);
      if(StringUtils.isEmpty(msg)) {
        throw new Exception("消息格式错误");
      }
    } catch (Exception e) {
      log.error("死信队列消息处理失败: {}", e.getMessage());
    }
  }

}
