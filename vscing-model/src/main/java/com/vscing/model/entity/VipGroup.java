package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * VipGroup
 *
 * @author vscing
 * @date 2025/6/1 19:00
 */
@Getter
@Setter
public class VipGroup extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "供应商ID")
  private Long supplierId;

  @Schema(description = "三方分组id")
  private Long tpGroupId;

  @Schema(description = "分组名称")
  private String groupName;

  @Schema(description = "分组别名")
  private String groupAlias;

  @Schema(description = "分组logo")
  private String groupLogo;

  @Schema(description = "品牌id")
  private Long brandId;

  @Schema(description = "品牌名称")
  private String brandName;

  @Schema(description = "品牌logo")
  private String brandLogo;

}
