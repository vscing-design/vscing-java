package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CouponListVo
 *
 * @author vscing
 * @date 2025/4/4 16:16
 */
@Data
public class CouponListVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "用户ID")
  private Long userId;

  @Schema(description = "订单号")
  private String orderSn;

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

  @Schema(description = "核销日期")
  private LocalDateTime verifyAt;

  @Schema(description = "作废日期")
  private LocalDateTime cancelAt;

  @Schema(description = "创建时间")
  private LocalDateTime createdAt;

  @Schema(description = "创建者ID")
  private Long createdBy;

  @Schema(description = "更新时间")
  private LocalDateTime updatedAt;

  @Schema(description = "更新者ID")
  private Long updatedBy;

  @Schema(description = "删除时间")
  private LocalDateTime deletedAt;

  @Schema(description = "删除者ID")
  private Long deletedBy;

}
