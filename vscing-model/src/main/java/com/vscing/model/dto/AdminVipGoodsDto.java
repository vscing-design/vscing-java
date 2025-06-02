package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * AdminVipGoodsDto
 *
 * @author vscing
 * @date 2025/6/2 16:04
 */
@Data
public class AdminVipGoodsDto {

  @Schema(description = "自定义商品 ID")
  private Long id;

  @Schema(description = "三方商品 ID")
  private Long tpGoodsId;

  @Schema(description = "商品名称")
  private String goodsName;

  @Schema(description = "自定义商品分组 ID")
  private Long groupId;

  @Schema(description = "商品分组三方 ID")
  private Long tpGroupId;

  @Schema(description = "分组名称")
  private String groupName;

  @Schema(description = "三方品牌id")
  private Long brandId;

  @Schema(description = "品牌名称")
  private String brandName;

  @Schema(description = "商品状态 1 出售 2 下架")
  private Integer goodsStatus;

  @Schema(description = "商品类型 1 卡密 2 直冲")
  private Integer goodsType;

  @Schema(description = "商品价格变化 0 全部 1 上涨、2 下降、3 不变")
  private Integer priceChange;

}
