package com.vscing.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * ServiceException
 *
 * @author vscing
 * @date 2024/12/23 21:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public final class ServiceException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 错误码
   */
  private Integer code;

  /**
   * 错误提示
   */
  private String message;

  /**
   * 错误明细，内部调试错误
   */
  private String detailMessage;

  public ServiceException(String message) {
    this.message = message;
  }

  public ServiceException(String message, Integer code) {
    this.message = message;
    this.code = code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public ServiceException setMessage(String message) {
    this.message = message;
    return this;
  }

  public ServiceException setDetailMessage(String detailMessage) {
    this.detailMessage = detailMessage;
    return this;
  }
}
