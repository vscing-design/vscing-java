package com.vscing.common.exception;

/**
 * GlobalExceptionHandler
 *
 * @author vscing
 * @date 2024/12/18 21:11
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.data.redis.RedisConnectionFailureException;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(RedisConnectionFailureException.class)
  public ResponseEntity<String> handleRedisConnectionFailure(RedisConnectionFailureException ex) {
    // 日志记录错误
    logger.error("Redis connection failed", ex);
    // 返回自定义的错误消息和状态码
    return new ResponseEntity<>("Failed to connect to Redis server.", HttpStatus.SERVICE_UNAVAILABLE);
  }

}
