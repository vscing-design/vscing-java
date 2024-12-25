package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * AdminUserOrganizationVo
 *
 * @author vscing
 * @date 2024/12/25 20:02
 */
@Data
public class AdminUserOrganizationVo {

  @Schema(description = "后台用户Id")
  private Long adminUserId;

  @Schema(description = "主键Id")
  private Long id;

  @Schema(description = "父级Id")
  private Long parentId;

  @Schema(description = "菜单名称")
  private String name;

  @Schema(description = "机构类型：1->企业")
  private Integer type;

  @Schema(description = "排序")
  private Integer sortOrder;

  @Schema(description = "创建时间")
  private LocalDateTime createdAt;

  @Schema(description = "创建者ID")
  private Long createdBy;

  @Schema(description = "更新时间")
  private LocalDateTime updatedAt;

  @Schema(description = "更新者ID")
  private Long updatedBy;

}
