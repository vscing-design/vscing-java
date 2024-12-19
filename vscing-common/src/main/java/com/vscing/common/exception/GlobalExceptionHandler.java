package com.vscing.common.exception;

/**
 * GlobalExceptionHandler
 *
 * @author vscing
 * @date 2024/12/18 21:11
 */
import com.vscing.common.api.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.data.redis.RedisConnectionFailureException;

import java.sql.SQLNonTransientConnectionException;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(RedisConnectionFailureException.class)
  public ResponseEntity<Object> handleRedisConnectionFailure(RedisConnectionFailureException ex) {
    String errorMessage = "Redis connection error: " + ex.getMessage();
    // 日志记录错误
    logger.error("RedisConnectionFailureException caught: {}", ex.getMessage(), ex);
    // 返回自定义的错误消息和状态码
    return new ResponseEntity<>(CommonResult.failed(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * 处理 SQLNonTransientConnectionException 异常。
   */
  @ExceptionHandler(SQLNonTransientConnectionException.class)
  public ResponseEntity<Object> handleSQLNonTransientConnectionException(SQLNonTransientConnectionException ex) {
    String errorMessage = "Database connection error: " + ex.getMessage();
    // 日志记录错误
    logger.error("SQLNonTransientConnectionException caught: {}", ex.getMessage(), ex);
    // 返回自定义的错误消息和状态码
    return new ResponseEntity<>(CommonResult.failed(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * 通用异常处理方法，捕获所有其他未被捕获的异常。
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAllExceptions(Exception ex) {
    // 自定义错误信息
    String errorMessage = "An unexpected error occurred: " + ex.getMessage();

    // 日志记录（这里假设你有一个日志框架，如 SLF4J）
    logger.error("Unexpected exception caught: {}", ex.getMessage(), ex);

    // 返回自定义响应
    return new ResponseEntity<>(CommonResult.failed(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
