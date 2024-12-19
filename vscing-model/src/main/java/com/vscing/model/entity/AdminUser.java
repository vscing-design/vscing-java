package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * AdminUser
 *
 * @author vscing
 * @date 2024/12/14 23:33
 */
@Getter
@Setter
public class AdminUser extends BaseEntity {

  @Schema(description = "用户id")
  private Long id;

  @Size(min = 3, max = 50, message = "用户名长度必须在3到50个字符之间")
  @Schema(description = "帐号")
  private String username;

  @Size(min = 6, max = 128, message = "密码长度必须在6到128个字符之间")
  @Schema(description = "密码")
  private String password;

  @Schema(description = "帐号启用状态：1->启用 2->禁用")
  private Integer status;

  @Schema(description = "手机号")
  private String phone;

  @Schema(description = "昵称")
  private String nickname;

  @Schema(description = "邮箱")
  private String email;

  @Schema(description = "头像")
  private String avatar;

  @Schema(description = "备注")
  private String notes;

  @Schema(description = "最后登陆IP")
  private String lastIp;

  @Schema(description = "最后登陆时间")
  private LocalDateTime loginAt;

  @Schema(description = "单点登陆token")
  private String token;

}
