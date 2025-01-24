package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * OrderApiSeatListVo
 *
 * @author vscing
 * @date 2025/1/19 12:35
 */
@Data
public class OrderApiSeatListVo {

  @Schema(description = "座位名称")
  private String seatName;

  @Schema(description = "座位原价")
  private BigDecimal maxPrice;

  @Schema(description = "座位售价")
  private BigDecimal minPrice;

  @Schema(description = "座位优惠金额")
  private BigDecimal discount;

}
