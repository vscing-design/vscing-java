package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * AdminUserListVo
 *
 * @author vscing
 * @date 2024/12/19 22:44
 */
@Data
public class AdminUserListVo {

  @Schema(description = "用户id")
  private Long id;

  @Schema(description = "账户")
  private String username;

  @Schema(description = "手机号")
  private String phone;

  @Schema(description = "邮箱")
  private String email;

  @Schema(description = "帐号启用状态：1->启用 2->禁用")
  private Integer status;

  @Schema(description = "头像")
  private String avatar;

  @Schema(description = "备注")
  private String notes;

  @Schema(description = "最后登陆IP")
  private String lastIp;

  @Schema(description = "最后登陆时间")
  private LocalDateTime loginAt;

  @Schema(description = "创建时间")
  private LocalDateTime createdAt;

  @Schema(description = "创建者ID")
  private Long createdBy;

  @Schema(description = "更新时间")
  private String updatedAt;

  @Schema(description = "更新者ID")
  private Long updatedBy;
}
