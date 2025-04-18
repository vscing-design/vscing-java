package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * CouponListDto
 *
 * @author vscing
 * @date 2025/3/23 00:54
 */
@Data
public class CouponListDto {

  @Schema(description = "优惠券状态 1 待核销 2 已核销 3 已过期 4 已作废")
  private Integer status;

  @Schema(description = "优惠券名称")
  private String title;

  @Schema(description = "手机号")
  private String phone;

  @Schema(description = "优惠券码")
  private String code;

  @Schema(description = "优惠券属性 1 单人 2 双人 3 多人")
  private Integer attr;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "优惠券开始日期")
  private LocalDateTime startTime;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "优惠券结束日期")
  private LocalDateTime endTime;

  @Schema(description = "作废原因 1 全部 2 错误券作废 3 订单退款")
  private Integer reason;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "作废开始日期")
  private LocalDateTime cancelStartTime;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "作废结束日期")
  private LocalDateTime cancelEndTime;

  @Schema(description = "订单号")
  private String orderSn;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "订单创建开始日期")
  private LocalDateTime createStartTime;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "订单创建结束日期")
  private LocalDateTime createEndTime;

}
