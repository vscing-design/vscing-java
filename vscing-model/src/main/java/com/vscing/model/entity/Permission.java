package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Menu
 *
 * @author vscing
 * @date 2024/12/22 00:41
 */
@Getter
@Setter
public class Permission extends BaseEntity {

  @Schema(description = "ID")
  private Long id;

  @Schema(description = "菜单ID")
  private Long menuId;

  @NotNull(message = "名称不能为空")
  @Schema(description = "名称")
  private String name;

  @NotNull(message = "权限不能为空")
  @Schema(description = "权限")
  private String code;

  @Schema(description = "状态：1->显示 2->隐藏")
  private Integer status;
}
