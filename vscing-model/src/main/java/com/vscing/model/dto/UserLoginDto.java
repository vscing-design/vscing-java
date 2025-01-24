package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * UserLoginDto
 *
 * @author vscing
 * @date 2025/1/6 12:27
 */
@Data
public class UserLoginDto {

  @NotNull(message = "平台不能为空")
  @Schema(description = "平台 wechat 微信小程序 alipay 支付宝小程序")
  private String platform;

  @NotNull(message = "code不能为空")
  @Schema(description = "login的code")
  private String code;

}
