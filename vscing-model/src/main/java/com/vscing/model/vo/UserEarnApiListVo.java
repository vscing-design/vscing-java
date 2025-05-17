package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * UserEarnApiListVo
 *
 * @author vscing
 * @date 2025/3/7 21:39
 */
@Data
public class UserEarnApiListVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "关联数量")
  private Integer withQuantity;

  @Schema(description = "关联用户手机号")
  private String withPhone;

  @Schema(description = "下单平台 1 微信小程序 2 支付宝小程序 3 百度小程序 21 淘宝 22 咸鱼 23 拼多多 24 微信 25 抖音 50 开放平台商户")
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
