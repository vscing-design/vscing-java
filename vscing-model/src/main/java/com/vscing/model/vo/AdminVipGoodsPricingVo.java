package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * AdminVipGoodsPricingVo
 *
 * @author vscing
 * @date 2025/6/2 23:57
 */
@Data
public class AdminVipGoodsPricingVo {

  @Schema(description = "自定义商品 ID")
  private Long goodsId;

  @Schema(description = "自定义商品分组 ID")
  private Long groupId;

  @Schema(description = "商品名称")
  private String goodsName;

  @Schema(description = "库存")
  private Integer stock;

  @Schema(description = "加价价格 元")
  private BigDecimal markupPrice;

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

  @Schema(description = "商品类型 1 卡密 2 直冲")
  private Integer goodsType;

}
