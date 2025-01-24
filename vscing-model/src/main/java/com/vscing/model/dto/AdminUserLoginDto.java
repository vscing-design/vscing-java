package com.vscing.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * AdminUserLoginDto
 *
 * @author vscing
 * @date 2024/12/14 23:24
 */
@Data
public class AdminUserLoginDto {
  @NotNull(message = "用户名不能为空")
  @Size(min = 3, max = 50, message = "用户名长度必须在3到50个字符之间")
  private String username;

  @NotNull(message = "密码不能为空")
  @Size(min = 6, max = 128, message = "密码长度必须在6到128个字符之间")
  private String password;
}
