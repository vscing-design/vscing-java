package com.vscing.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * AdminUserRolesRequest
 *
 * @author vscing
 * @date 2024/12/24 00:24
 */
@Data
public class AdminUserRolesRequest {

  @NotNull(message = "用户ID不能为空")
  @Schema(description = "用户ID")
  private Long adminUserId;

  @NotNull(message = "角色不能为空")
  @Schema(description = "角色ID列表")
  private List<Long> roleIds;

}
