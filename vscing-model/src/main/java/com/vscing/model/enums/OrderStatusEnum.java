package com.vscing.model.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

import java.util.Arrays;

/**
 * OrderStatus 订单状态
 *
 * @author vscing
 * @date 2025/2/6 22:03
 */
@Getter
public enum OrderStatusEnum {

  PENDING_PAYMENT(1, "待付款"),
  PENDING_ISSUANCE(2, "待出票"),
  ISSUANCE_IN_PROGRESS(3, "出票中"),
  ISSUED(4, "已出票"),
  CANCELLED(5, "已取消"),
  REFUND_IN_PROGRESS(6, "退款中"),
  REFUNDED(7, "退款完成");

  /**
   * 状态码
   */
  private final int status;

  /**
   * 状态描述
   */
  private final String desc;

  /**
   * 手动定义全参构造函数
  */
  OrderStatusEnum(int status, String desc) {
    this.status = status;
    this.desc = desc;
  }

  /**
   * 状态码查状态描述
   *
   * @param status 状态
   */
  public static String findByStatus(int status) {
    // 直接进行枚举查找，无需对整数进行空检查
    return Arrays.stream(OrderStatusEnum.values())
        .filter(statusEnum -> statusEnum.getStatus() == status)
        .findFirst()
        .map(OrderStatusEnum::getDesc)
        .orElse(StrUtil.EMPTY);
  }

}
