package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * SeatPriceMapVo
 *
 * @author vscing
 * @date 2025/1/1 21:58
 */
@Data
public class SeatPriceMapVo {

  @Schema(description = "销售价")
  private String salesPrice;

  @Schema(description = "供货价")
  private String supplyPrice;

  @Schema(description = "官方价")
  private String officialPrice;

  @Schema(description = "利润")
  private String profit;

}
