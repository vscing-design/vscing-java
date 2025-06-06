package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryVipGoodsDto extends QueryDto {

  @Schema(description = "当前页，默认1")
  private Integer pageNum = 1;

  @Max(value = 50, message = "单页数量最大值为50")
  @Schema(description = "单页数量，默认10，最大50")
  private Integer pageSize = 10;

  @Schema(description = "分组ID")
  private Long groupId;

  @Schema(description = "商品类型 1 卡密 2 直冲")
  private Integer goodsType;

//  @Schema(description = "分组名称")
//  private String groupName;

}
