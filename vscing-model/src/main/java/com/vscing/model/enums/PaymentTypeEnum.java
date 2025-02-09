package com.vscing.model.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

import java.util.Arrays;

/**
 * PaymentTypeEnum 支付方式
 *
 * @author vscing
 * @date 2025/2/9 00:58
 */
@Getter
public enum PaymentTypeEnum {

  WECHAT(1, "wechat"),
  ALIPAY(2, "alipay");

  /**
   * 标识
   */
  private int code;

  /**
   * 支付类型
   */
  private String paymentType;

  /**
   * 手动定义全参构造函数
   */
  PaymentTypeEnum(int code, String paymentType) {
    this.code = code;
    this.paymentType = paymentType;
  }

  /**
   * 标识查支付类型
   *
   * @param code 标识
   */
  public static String findByCode(int code) {
    // 直接进行枚举查找，无需对整数进行空检查
    return Arrays.stream(PaymentTypeEnum.values())
        .filter(record -> record.getCode() == code)
        .findFirst()
        .map(PaymentTypeEnum::getPaymentType)
        .orElse(StrUtil.EMPTY);
  }


}
