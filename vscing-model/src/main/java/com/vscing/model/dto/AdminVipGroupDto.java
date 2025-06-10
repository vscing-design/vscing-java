package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * AdminVipGroupDto
 *
 * @author vscing
 * @date 2025/6/2 15:54
 */
@Data
public class AdminVipGroupDto {

  @Schema(description = "供应商名称")
  private String supplierName;

  @Schema(description = "分组名称")
  private String groupName;

  @Schema(description = "自定义分组id")
  private Long id;

  @Schema(description = "三方分组id")
  private Long tpGroupId;

  @Schema(description = "品牌名称")
  private String brandName;

}
