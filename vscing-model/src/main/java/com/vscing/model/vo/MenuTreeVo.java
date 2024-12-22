package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * MenuTreeVo
 *
 * @author vscing
 * @date 2024/12/22 21:08
 */
@Getter
@Setter
public class MenuTreeVo {

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

  @Schema(description = "创建时间")
  private LocalDateTime createdAt;

  @Schema(description = "创建者ID")
  private Long createdBy;

  @Schema(description = "更新时间")
  private LocalDateTime updatedAt;

  @Schema(description = "更新者ID")
  private Long updatedBy;

  @Schema(description = "子数据列表")
  private List<MenuTreeVo> children;

}
