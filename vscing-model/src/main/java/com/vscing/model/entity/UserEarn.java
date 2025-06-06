package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * UserEarn 用户收益表
 *
 * @author vscing
 * @date 2025/3/2 16:30
 */
@Getter
@Setter
public class UserEarn extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "用户ID")
  private Long userId;

  @Schema(description = "关联订单ID")
  private Long withOrderId;

  @Schema(description = "关联用户ID")
  private Long withUserId;

  @Schema(description = "下单平台 1 微信小程序 2 支付宝小程序 3 百度小程序 21 淘宝 22 咸鱼 23 拼多多 24 微信 25 抖音 50 开放平台商户")
  private Integer platform;

  @Schema(description = "推广类型：1 邀请好友 2 电影票订单")
  private Integer earnType;

  @Schema(description = "推广金额")
  private BigDecimal earnAmount;

  @Schema(description = "任务状态 1 进行中 2 成功 3 失败")
  private Integer status;

}
