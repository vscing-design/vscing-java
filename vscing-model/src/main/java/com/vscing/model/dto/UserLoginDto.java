package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * UserLoginDto
 *
 * @author vscing
 * @date 2025/1/6 12:27
 */
public class UserLoginDto {

  @NotNull(message = "平台不能为空")
  @Schema(description = "平台 1 微信小程序 2 支付宝小程序")
  private String platform;

  @NotNull(message = "code不能为空")
  @Schema(description = "wx.login() 获取 临时登录凭证code https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html")
  private String code;

}
