package com.vscing.model.enums;

import lombok.Getter;

/**
 * JfshouOrderSubmitEnum 海威创建订单后响应码
 *
 * @author vscing
 * @date 2025/2/6 22:52
 */
@Getter
public enum JfshouOrderSubmitResponseCodeEnum {

  SUCCESS_200(200, "success"),
  SUCCESS_NEGATIVE_530(-530, "success"),
  SUCCESS_9999(9999, "success"),
  SUCCESS_9008(9008, "success"),
  SUCCESS_9011(9011, "success"),

  ERROR_9001(9001, "error"),
  ERROR_9005(9005, "error"),
  ERROR_9006(9006, "error"),
  ERROR_9007(9007, "error"),
  ERROR_9009(9009, "error");

  /**
   * 标识
   */
  private final int code;

  /**
   * 标识名称
   */
  private final String codeName;

  /**
   * 手动定义全参构造函数
   */
  JfshouOrderSubmitResponseCodeEnum(int code, String codeName) {
    this.code = code;
    this.codeName = codeName;
  }

  /**
   * 根据状态码查找对应的枚举实例
   *
   * @param code 状态码
   * @return 对应的枚举实例，如果找不到则返回 null
   */
  public static JfshouOrderSubmitResponseCodeEnum fromCode(int code) {
    for (JfshouOrderSubmitResponseCodeEnum responseCode : values()) {
      if (responseCode.getCode() == code) {
        return responseCode;
      }
    }
    return null;
  }

  /**
   * 判断给定的状态码是否属于成功类别
   *
   * @param code 状态码
   * @return 如果状态码属于成功类别，则返回 true；否则返回 false
   */
  public static boolean isSuccessCode(int code) {
    JfshouOrderSubmitResponseCodeEnum responseCode = fromCode(code);
    return responseCode != null && "success".equalsIgnoreCase(responseCode.getCodeName());
  }

  /**
   * 判断给定的状态码是否属于错误类别
   *
   * @param code 状态码
   * @return 如果状态码属于错误类别，则返回 true；否则返回 false
   */
  public static boolean isErrorCode(int code) {
    JfshouOrderSubmitResponseCodeEnum responseCode = fromCode(code);
    return responseCode != null && "error".equalsIgnoreCase(responseCode.getCodeName());
  }

}
