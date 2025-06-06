package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class QueryVipGoods {

  @Schema(description = "商品ID")
  private Long goodsId;

  @Schema(description = "商品分组 id")
  private Long groupId;

  @Schema(description = "库存")
  private Integer stock;

  @Schema(description = "商品 logo")
  private String goodsLogo;

  @Schema(description = "商品名称")
  private String goodsName;

  @Schema(description = "商品单价 元")
  private BigDecimal goodsPrice;

  @Schema(description = "市场价格 元")
  private BigDecimal marketPrice;

  @Schema(description = "商品状态 1 出售 2 下架")
  private Integer goodsStatus;

  @Schema(description = "商品类型 1 卡密 2 直冲")
  private Integer goodsType;

//  @Schema(description = "充值字段")
//  private List<QueryVipGoodsAttach> attachList;

}
