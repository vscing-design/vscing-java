package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * MerchantOrderCountVo
 *
 * @author vscing
 * @date 2025/4/26 09:47
 */
@Data
public class MerchantOrderCountVo {

  @Schema(description = "商户ID")
  private Long merchantId;

  @Schema(description = "订单ID")
  private Long orderId;

  @Schema(description = "订单日期")
  private LocalDateTime orderDate;

  @Schema(description = "商品类型 1 电影票")
  private Integer productType;

  @Schema(description = "订单数量")
  private LocalDateTime orderQuantity;

  @Schema(description = "订单金额")
  private LocalDateTime orderAmount;

}
