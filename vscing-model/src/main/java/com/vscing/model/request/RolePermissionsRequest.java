package com.vscing.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * RolePermissionsRequest
 *
 * @author vscing
 * @date 2025/1/14 10:22
 */
@Data
public class RolePermissionsRequest {

  @NotNull(message = "角色ID不能为空")
  @Schema(description = "角色Id")
  private String roleId;

  @NotNull(message = "按钮不能为空")
  @Schema(description = "按钮ID列表")
  private List<Long> permissionIds;

}
