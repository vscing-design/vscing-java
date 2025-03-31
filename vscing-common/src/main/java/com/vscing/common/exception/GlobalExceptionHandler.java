package com.vscing.common.exception;

/**
 * GlobalExceptionHandler
 *
 * @author vscing
 * @date 2024/12/18 21:11
 */

import com.vscing.common.api.CommonResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLNonTransientConnectionException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @Value("${app.debug}")
  private Boolean appDebug;

  /**
   * 请求方式不支持
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                    HttpServletRequest request) {
    String errorMessage = "请求方式不支持: " + ex.getMessage();
    log.error("请求地址'{}',不支持'{}'请求", request.getRequestURI(), ex.getMethod());
    // 返回自定义的错误消息和状态码
    return new ResponseEntity<>(CommonResult.failed(appDebug ? errorMessage : "请求方式不支持"), HttpStatus.METHOD_NOT_ALLOWED);
  }

  /**
   * 请求路径中缺少必需的路径变量
   */
  @ExceptionHandler(MissingPathVariableException.class)
  public ResponseEntity<Object> handleMissingPathVariableException(MissingPathVariableException ex, HttpServletRequest request) {
    String errorMessage = "请求路径中缺少必需的路径变量: " + ex.getMessage();
    log.error("请求路径中缺少必需的路径变量'{}',发生系统异常.", request.getRequestURI());
    // 返回自定义的错误消息和状态码
    return new ResponseEntity<>(CommonResult.failed(appDebug ? errorMessage : "请求路径中缺少必需的路径变量"), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * 请求参数类型不匹配
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
    String errorMessage = "请求参数类型不匹配: " + ex.getMessage();
    log.error("请求参数类型不匹配'{}',发生系统异常.", request.getRequestURI());
    // 返回自定义的错误消息和状态码
    return new ResponseEntity<>(CommonResult.failed(appDebug ? errorMessage : "请求参数类型不匹配"), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * 处理入参格式错误
   */
  @ExceptionHandler(value = {MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
  public ResponseEntity<CommonResult<Object>> handleValidationExceptions(Exception ex, HttpServletRequest request) {
    String errorMessage = "入参格式错误: " + ex.getMessage();
    log.error("入参格式错误'{}',发生系统异常.", request.getRequestURI());
    // 可以根据需要定制错误信息
    return new ResponseEntity<>(CommonResult.failed(appDebug ? errorMessage : "入参格式错误"), HttpStatus.BAD_REQUEST);
  }

  /**
   * 找不到路由
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    String errorMessage = "路由不存在: " + ex.getMessage();
    log.error("请求地址'{}'不存在.", requestURI);
    return new ResponseEntity<>(CommonResult.failed(appDebug ? errorMessage : "路由不存在"), HttpStatusCode.valueOf(cn.hutool.http.HttpStatus.HTTP_NOT_FOUND));
  }

  /**
   * 找不到资源
   */
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    String errorMessage = "资源未找到: " + ex.getMessage();
    log.error("请求资源'{}'未找到.", requestURI);
    return new ResponseEntity<>(CommonResult.failed(appDebug ? errorMessage : "资源未找到"), HttpStatus.NOT_FOUND);
  }

  /**
   * 处理 RedisConnectionFailureException 异常。
   */
  @ExceptionHandler(RedisConnectionFailureException.class)
  public ResponseEntity<Object> handleRedisConnectionFailure(RedisConnectionFailureException ex) {
    String errorMessage = "Redis connection error: " + ex.getMessage();
    // 日志记录错误
    log.error("RedisConnectionFailureException caught: {}", ex.getMessage(), ex);
    // 返回自定义的错误消息和状态码
    return new ResponseEntity<>(CommonResult.failed(appDebug ? errorMessage : "服务器异常"), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * 处理 SQLNonTransientConnectionException 异常。
   */
  @ExceptionHandler(SQLNonTransientConnectionException.class)
  public ResponseEntity<Object> handleSQLNonTransientConnectionException(SQLNonTransientConnectionException ex) {
    String errorMessage = "Database connection error: " + ex.getMessage();
    // 日志记录错误
    log.error("SQLNonTransientConnectionException caught: {}", ex.getMessage(), ex);
    // 返回自定义的错误消息和状态码
    return new ResponseEntity<>(CommonResult.failed(appDebug ? errorMessage : "服务器异常"), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * 通用异常处理方法，捕获所有其他未被捕获的异常。
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAllExceptions(Exception ex) {
    // 自定义错误信息
    String errorMessage = "通用异常处理方法: " + ex.getMessage();

    // 日志记录（这里假设你有一个日志框架，如 SLF4J）
    log.error("通用异常处理方法: {}", ex.getMessage(), ex);

    // 返回自定义响应
    return new ResponseEntity<>(CommonResult.failed(appDebug ? errorMessage : "服务器异常"), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
