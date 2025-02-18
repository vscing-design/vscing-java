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
  @Schema(description = "平台 wechat 微信小程序 alipay 支付宝小程序 baidu 百度小程序")
  private String platform;

  @NotNull(message = "code不能为空")
  @Schema(description = "微信、支付宝、百度登陆时候的login生成的code。" +
      "微信、支付宝获取手机号时候的code，百度获取手机号时候的encryptedData 和 iv 组成的json字符串 " +
      "{\"encryptedData\":\"xxx\",\"iv\":\"xxx\"}，" +
      "！！！注意：百度获取手机号时候先调用checkSession方法判断是否过期，如果过期需要先调用登陆接口后再调用获取手机号接口")
  private String code;

}
