package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * AdminVipGoodsVo
 *
 * @author vscing
 * @date 2025/6/2 15:59
 */
@Data
public class AdminVipGoodsVo {

  @Schema(description = "自定义商品 ID")
  private Long id;

  @Schema(description = "供应商 ID")
  private Long supplierId;

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

  @Schema(description = "库存")
  private Integer stock;

  @Schema(description = "旧商品单价 元")
  private BigDecimal oldGoodsPrice;

  @Schema(description = "商品单价 元")
  private BigDecimal goodsPrice;

  @Schema(description = "旧市场价格 元")
  private BigDecimal oldMarketPrice;

  @Schema(description = "市场价格 元")
  private BigDecimal marketPrice;

  @Schema(description = "商品状态 1 出售 2 下架")
  private Integer goodsStatus;

  @Schema(description = "商品 logo")
  private String goodsLogo;

  @Schema(description = "商品类型 1 卡密 2 直冲")
  private Integer goodsType;

  @Schema(description = "单次最小购买数量")
  private Integer minBuy;

  @Schema(description = "单次最大购买数量")
  private Integer maxBuy;

  @Schema(description = "商品详情")
  private String details;

}
