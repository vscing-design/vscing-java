package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * UserInviteQrcodeDto
 *
 * @author vscing
 * @date 2025/3/7 22:25
 */
@Data
public class UserInviteQrcodeDto {

  @NotNull(message = "平台不能为空")
  @Schema(description = "平台 wechat 微信小程序 alipay 支付宝小程序 baidu 百度小程序")
  private String platform;

}
