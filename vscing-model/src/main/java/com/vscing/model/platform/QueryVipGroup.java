package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryVipGroup {

  @Schema(description = "分组id")
  private Long groupId;

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
