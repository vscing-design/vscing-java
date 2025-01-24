package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * OrderPriceVo
 *
 * @author vscing
 * @date 2025/1/5 10:54
 */
@Data
public class OrderPriceVo {

  @Schema(description = "订单总价格")
  private BigDecimal totalPrice;

  @Schema(description = "订单官方价")
  private BigDecimal officialPrice;

  @Schema(description = "订单结算价")
  private BigDecimal settlementPrice;

}
