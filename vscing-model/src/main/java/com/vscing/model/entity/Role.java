package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Role
 *
 * @author vscing
 * @date 2024/12/22 00:46
 */
@Getter
@Setter
public class Role extends BaseEntity {

  @Schema(description = "角色id")
  private Long id;

  @NotNull(message = "角色名称不能为空")
  @Schema(description = "角色名称")
  private String name;

  @Schema(description = "角色排序")
  private Integer sortOrder;

  @Schema(description = "角色备注")
  private String notes;

}
