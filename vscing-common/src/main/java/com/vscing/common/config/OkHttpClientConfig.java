package com.vscing.common.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * OkHttpConfiguration
 *
 * @author luoqifeng
 */
@Configuration
@Slf4j
public class OkHttpClientConfig {

  @Value("${ok.http.connect-timeout}")
  private Integer connectTimeout;

  @Value("${ok.http.read-timeout}")
  private Integer readTimeout;

  @Value("${ok.http.write-timeout}")
  private Integer writeTimeout;

  @Value("${ok.http.max-idle-connections}")
  private Integer maxIdleConnections;

  @Value("${ok.http.keep-alive-duration}")
  private Long keepAliveDuration;

  @Bean
  public OkHttpClient okHttpClient() {

    // 创建 HttpLoggingInterceptor 并设置日志级别为 BODY
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(log::info);
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    return new OkHttpClient.Builder()
        // 启用连接失败时的自动重试
        .retryOnConnectionFailure(true)
        .connectionPool(pool())
        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
        .readTimeout(readTimeout, TimeUnit.SECONDS)
        .writeTimeout(writeTimeout, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build();
  }

  @Bean
  public ConnectionPool pool() {
    return new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.SECONDS);
  }
}