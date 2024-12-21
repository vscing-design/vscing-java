package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Menu
 *
 * @author vscing
 * @date 2024/12/22 00:41
 */
@Getter
@Setter
public class Menu extends BaseEntity {

  @Schema(description = "菜单id")
  private Long id;

  @Schema(description = "菜单父id")
  private Long parentId;

  @Schema(description = "菜单名称")
  private String name;

  @Schema(description = "所属系统")
  private String system;

  @Schema(description = "icon")
  private String icon;

  @Schema(description = "前端组件")
  private String component;

  @Schema(description = "启用状态：1->启用 2->禁用")
  private Integer status;
}
