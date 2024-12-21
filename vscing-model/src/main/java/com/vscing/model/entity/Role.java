package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
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

  @Schema(description = "角色名称")
  private String name;

  @Schema(description = "角色排序")
  private Integer sortOrder;

  @Schema(description = "角色备注")
  private String notes;

}
