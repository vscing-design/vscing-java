package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import com.vscing.model.vo.MenuTreeVo;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
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
@AutoMappers({
    @AutoMapper(target = MenuTreeVo.class)
})
public class Menu extends BaseEntity {

  @Schema(description = "菜单id")
  private Long id;

  @Schema(description = "菜单父id")
  private Long parentId;

  @NotNull(message = "菜单名称不能为空")
  @Schema(description = "菜单名称")
  private String name;

  @Schema(description = "所属系统")
  private String system;

  @Schema(description = "icon")
  private String icon;

  @Schema(description = "路由地址")
  private String path;

  @Schema(description = "前端组件")
  private String component;

  @Schema(description = "状态：1->显示 2->隐藏")
  private Integer status;
}
