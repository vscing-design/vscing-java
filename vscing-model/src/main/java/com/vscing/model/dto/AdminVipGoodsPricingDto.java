package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * AdminVipGoodsPricingDto
 *
 * @author vscing
 * @date 2025/6/2 23:58
 */
@Data
public class AdminVipGoodsPricingDto {

  @Schema(description = "自定义商品 ID")
  private Long goodsId;

  @Schema(description = "商户ID")
  private Long merchantId;

  @Schema(description = "商品名称")
  private String goodsName;

  @Schema(description = "自定义商品分组 ID")
  private Long groupId;

  @Schema(description = "商品状态 1 出售 2 下架")
  private Integer goodsStatus;

  @Schema(description = "商品类型 1 卡密 2 直冲")
  private Integer goodsType;

  @Schema(description = "商品价格变化 0 全部 1 上涨、2 下降、3 不变")
  private Integer priceChange;

}
