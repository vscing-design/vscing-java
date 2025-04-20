package com.vscing.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * MerchantUserPasswordRequest
 *
 * @author vscing
 * @date 2025/4/20 22:38
 */
@Data
public class MerchantUserPasswordRequest {

  @NotNull(message = "密码不能为空")
  @Size(min = 6, max = 128, message = "密码长度必须在6到128个字符之间")
  @Schema(description = "密码")
  private String password;

  @NotNull(message = "确认密码不能为空")
  @Size(min = 6, max = 128, message = "确认密码长度必须在6到128个字符之间")
  @Schema(description = "确认密码")
  private String confirmPassword;

}
