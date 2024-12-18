package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * AdminUser
 *
 * @author vscing
 * @date 2024/12/14 23:33
 */
@Data
public class AdminUser extends BaseEntity {

  private Long id;

  @NotNull(message = "用户名不能为空")
  @Size(min = 3, max = 50, message = "用户名长度必须在3到50个字符之间")
  private String username;

  @NotNull(message = "密码不能为空")
  @Size(min = 6, max = 128, message = "密码长度必须在6到128个字符之间")
  private String password;

  @Schema(description = "帐号启用状态：1->启用 2->禁用")
  private Integer status;

  private String phone;

  private String nickname;

  private String email;

  private String avatar;

  private String notes;

  private String lastIp;

  private LocalDateTime loginAt;

  private String token;

}
