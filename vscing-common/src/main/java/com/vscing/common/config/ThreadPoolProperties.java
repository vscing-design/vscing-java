package com.vscing.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ThreadPoolProperties
 *
 * @author vscing
 * @date 2024/12/26 23:33
 */
@Data
@Component
@ConfigurationProperties(prefix = "thread-pool")
public class ThreadPoolProperties {

  /**
   * 是否开启线程池
   */
  private boolean enabled;

  /**
   * 队列最大长度
   */
  private int queueCapacity;

  /**
   * 线程池维护线程所允许的空闲时间
   */
  private int keepAliveSeconds;

}
