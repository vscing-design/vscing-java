package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CouponApiDetailsVo
 *
 * @author vscing
 * @date 2025/3/22 17:11
 */
@Data
public class CouponApiDetailsVo {

  @Schema(description = "优惠券名称")
  private String title;

  @Schema(description = "手机号")
  private String phone;

  @Schema(description = "优惠券码")
  private String code;

  @Schema(description = "优惠券售价（元）")
  private BigDecimal salesAmount;

  @Schema(description = "优惠券面值（元）")
  private BigDecimal faceAmount;

  @Schema(description = "优惠券类型 1 电影票")
  private Integer type;

  @Schema(description = "优惠券属性 1 单人 2 双人 3 多人")
  private Integer attr;

  @Schema(description = "优惠券来源 1 抖音 2 淘宝 3 拼多多")
  private Integer source;

  @Schema(description = "优惠券开始日期")
  private LocalDateTime startTime;

  @Schema(description = "优惠券结束日期")
  private LocalDateTime endTime;

  @Schema(description = "优惠券标签")
  private String tags;

  @Schema(description = "条件")
  private String condition;

  @Schema(description = "优惠券状态 1 待核销 2 已核销 3 已过期 4 已作废")
  private Integer status;

  @Schema(description = "作废原因 1 全部 2 错误券作废 3 订单退款")
  private Integer reason;

}
