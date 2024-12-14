package com.vscing.admin.entity;

import com.vscing.common.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * AdminUser
 *
 * @author vscing
 * @date 2024/12/14 23:33
 */
@Getter
@Setter
public class AdminUser extends BaseEntity {
  private Long id;

  // 用户名称
  private String username;

  // 用户密码
  private String password;

  @Schema(description = "帐号启用状态：0->禁用；1->启用")
  private Integer status;
}
