package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * UserAuth
 *
 * @author vscing
 * @date 2025/1/10 13:26
 */
@Getter
@Setter
public class UserAuth extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "主键ID")
  private Long userId;

  @Schema(description = "下单平台 1 微信小程序 2 支付宝小程序 3 百度小程序 21 淘宝 22 咸鱼 23 拼多多 24 微信 25 抖音 50 开放平台商户")
  private Integer platform;

  @Schema(description = "uuid")
  private String uuid;

  @Schema(description = "openid")
  private String openid;

  @Schema(description = "分享二维码")
  private String inviteQrcode;

  @Schema(description = "最后登陆IP")
  private String lastIp;

  @Schema(description = "最后登陆时间")
  private LocalDateTime loginAt;

}
