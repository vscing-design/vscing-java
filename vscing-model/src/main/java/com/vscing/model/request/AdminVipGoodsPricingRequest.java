package com.vscing.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * AdminVipGoodsPricingRequest
 *
 * @author vscing
 * @date 2025/6/2 23:58
 */
@Data
public class AdminVipGoodsPricingRequest {

  @Schema(description = "ID")
  private Long id;

  @Schema(description = "商户ID")
  private Long merchantId;

  @Schema(description = "供应商ID")
  private Long supplierId;

  @Schema(description = "商品ID")
  private Long vipGoodsId;

  @Schema(description = "加价金额")
  private BigDecimal markupAmount;

  @Schema(description = "创建人ID")
  private Long createdBy;

}
