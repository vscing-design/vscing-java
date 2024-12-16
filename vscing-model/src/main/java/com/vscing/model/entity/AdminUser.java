package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
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

  private String username;

  private String password;

  @Schema(description = "帐号启用状态：1->启用 2->禁用")
  private Integer state;

  private String phone;

  private String nickname;

  private String email;

  private String avatar;

  private String desc;

  private String lastIp;

  private String loginAt;

  private String token;

}
