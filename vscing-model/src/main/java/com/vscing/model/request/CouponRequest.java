package com.vscing.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CouponRequest
 *
 * @author vscing
 * @date 2025/3/22 16:48
 */
@Data
public class CouponRequest {

  @NotNull(message = "优惠券名称不能为空")
  @Schema(description = "优惠券名称")
  private String title;

  @NotNull(message = "手机号不能为空")
  @Pattern(regexp = "^1([3456789])\\d{9}$", message = "手机号格式不正确")
  @Schema(description = "手机号")
  private String phone;

  @NotNull(message = "优惠券码不能为空")
  @Schema(description = "优惠券码")
  private String code;

  @NotNull(message = "优惠券售价（元）不能为空")
  @Schema(description = "优惠券售价（元）")
  private BigDecimal salesAmount;

  @NotNull(message = "优惠券面值（元）不能为空")
  @Schema(description = "优惠券面值（元）")
  private BigDecimal faceAmount;

  @Schema(description = "优惠券类型 1 电影票")
  private Integer type;

  @NotNull(message = "优惠券属性不能为空")
  @Schema(description = "优惠券属性 1 单人 2 双人 3 多人")
  private Integer attr;

  @NotNull(message = "优惠券来源不能为空")
  @Schema(description = "优惠券来源 1 抖音 2 淘宝 3 拼多多")
  private Integer source;

  @NotNull(message = "使用条件不能为空")
  @Schema(description = "使用条件")
  private String condition;

  @NotNull(message = "优惠券开始日期不能为空")
  @Schema(description = "优惠券开始日期")
  private LocalDateTime startTime;

  @NotNull(message = "优惠券结束日期不能为空")
  @Schema(description = "优惠券结束日期")
  private LocalDateTime endTime;

  @NotNull(message = "购买时间不能为空")
  @Schema(description = "购买时间")
  private LocalDateTime createdAt;

}
