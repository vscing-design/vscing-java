package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * MerchantGoodsListDto
 *
 * @author vscing
 * @date 2025/6/2 21:46
 */
@Data
public class MerchantGoodsListDto {

  @Schema(description = "分组名称")
  private String groupName;

  @Schema(description = "品牌名称")
  private String brandName;

  @Schema(description = "商品名称")
  private String goodsName;

  @Schema(description = "自定义商品 ID")
  private Long id;

  @Schema(description = "商品状态 1 出售 2 下架")
  private Integer goodsStatus;

  @Schema(description = "商品类型 1 卡密 2 直冲")
  private Integer goodsType;

}
