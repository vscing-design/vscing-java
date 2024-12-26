package com.vscing.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThreadPoolConfig
 *
 * @author vscing
 * @date 2024/12/26 23:23
 */
@Configuration
@EnableCaching
public class ThreadPoolConfig {

  @Autowired
  private ThreadPoolProperties threadPoolProperties;

  /**
   * 核心线程数 = cpu 核心数 + 1
   */
  private final int core = Runtime.getRuntime().availableProcessors() + 1;

  @Bean(name = "threadPoolTaskExecutor")
  public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    // 线程名称前缀
    executor.setThreadNamePrefix("Async-");
    // 核心线程数
    executor.setCorePoolSize(core);
    // 最大线程数
    executor.setMaxPoolSize(core * 2);
    // 队列容量
    executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
    // 线程空闲时间
    executor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
    // 拒绝策略
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    return executor;
  }

}
