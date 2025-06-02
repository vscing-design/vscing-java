package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * VipGoods
 *
 * @author vscing
 * @date 2025/6/1 19:04
 */
@Getter
@Setter
public class VipGoods extends BaseEntity {

  @Schema(description = "主键 ID")
  private Long id;

  @Schema(description = "供应商 ID")
  private Long supplierId;

  @Schema(description = "三方商品 id")
  private Long tpGoodsId;

  @Schema(description = "商品名称")
  private String goodsName;

  @Schema(description = "商品分组 id")
  private Long groupId;

  @Schema(description = "商品分组三方 id")
  private Long tpGroupId;

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

  @Schema(description = "增加价格 元")
  private BigDecimal markupPrice;

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
