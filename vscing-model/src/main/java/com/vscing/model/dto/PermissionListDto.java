package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * PermissionListDto
 *
 * @author vscing
 * @date 2024/12/25 00:02
 */
@Data
public class PermissionListDto {

  @Schema(description = "角色名称")
  private String name;

  @Schema(description = "权限")
  private Integer code;

  @Schema(description = "状态：1->显示 2->隐藏")
  private Integer status;

}
