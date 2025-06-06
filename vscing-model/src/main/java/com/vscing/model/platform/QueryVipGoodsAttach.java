package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryVipGoodsAttach {

  @Schema(description = "商品 ID")
  private Long goodsId;

  @Schema(description = "充值字段标题")
  private String title;

  @Schema(description = "充值字段提示")
  private String tip;

}
