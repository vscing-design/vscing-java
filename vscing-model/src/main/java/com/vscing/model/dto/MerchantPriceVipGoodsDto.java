package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * MerchantPriceVipGoodsDto
 *
 * @author vscing
 * @date 2025/6/4 23:00
 */
@Data
public class MerchantPriceVipGoodsDto {

  @Schema(description = "商户ID")
  private Long merchantId;

  @Schema(description = "会员卡商品ID集合")
  private List<Long> vipGoodsIds;

}
