package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AdminUserSaveDto
 *
 * @author vscing
 * @date 2024/12/23 20:30
 */
@Data
public class AdminUserSaveDto {

  @Schema(description = "主键")
  private Long id;

  @Schema(description = "用户名")
  private String username;

  @Schema(description = "手机号")
  private String phone;

  @Schema(description = "密码")
  private String password;

  @Schema(description = "机构")
  private List<Long> organizationIds;

  @Schema(description = "帐号启用状态：1->启用 2->禁用")
  private Integer status;

  @Schema(description = "最后登陆IP")
  private String lastIp;

  @Schema(description = "最后登陆时间")
  private LocalDateTime loginAt;

  @Schema(description = "创建人ID")
  private Long createdBy;

  @Schema(description = "修改人ID")
  private Long updatedBy;

}
