package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * UserEarnListVo
 *
 * @author vscing
 * @date 2025/3/2 23:38
 */
@Data
public class UserEarnListVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "用户ID")
  private Long userId;

  @Schema(description = "用户手机号")
  private String phone;

  @Schema(description = "关联订单ID")
  private Long withOrderId;

  @Schema(description = "关联订单号")
  private String withOrderSn;

  @Schema(description = "关联订单创建时间")
  private LocalDateTime withOrderCreatedAt;

  @Schema(description = "关联用户ID")
  private Long withUserId;

  @Schema(description = "关联用户手机号")
  private String withUserPhone;

  @Schema(description = "平台 1 微信小程序 2 支付宝小程序 3 百度小程序")
  private Integer platform;

  @Schema(description = "推广类型：1 邀请好友 2 电影票订单")
  private Integer earnType;

  @Schema(description = "推广金额")
  private BigDecimal earnAmount;

  @Schema(description = "任务状态 1 进行中 2 成功 3 失败")
  private Integer status;

  @Schema(description = "更新时间")
  private LocalDateTime updatedAt;

}
