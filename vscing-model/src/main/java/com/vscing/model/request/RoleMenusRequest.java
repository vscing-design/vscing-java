package com.vscing.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * RoleMenusRequest
 *
 * @author vscing
 * @date 2024/12/23 23:45
 */
@Data
public class RoleMenusRequest {

  @NotNull(message = "角色ID不能为空")
  @Schema(description = "角色Id")
  private Long roleId;

  @NotNull(message = "菜单不能为空")
  @Schema(description = "菜单ID列表")
  private List<Long> menuIds;

}
