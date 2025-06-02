package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * AdminVipGroupVo
 *
 * @author vscing
 * @date 2025/6/2 15:49
 */
@Data
public class AdminVipGroupVo {

  @Schema(description = "自定义分组id")
  private Long id;

  @Schema(description = "供应商ID")
  private Long supplierId;

  @Schema(description = "供应商名称")
  private String supplierName;

  @Schema(description = "三方分组id")
  private Long tpGroupId;

  @Schema(description = "分组名称")
  private String groupName;

  @Schema(description = "分组别名")
  private String groupAlias;

  @Schema(description = "分组logo")
  private String groupLogo;

  @Schema(description = "三方品牌id")
  private Long brandId;

  @Schema(description = "品牌名称")
  private String brandName;

  @Schema(description = "品牌logo")
  private String brandLogo;

}
