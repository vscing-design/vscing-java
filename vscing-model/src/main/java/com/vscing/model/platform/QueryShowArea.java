package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class QueryShowArea {

  @Schema(description = "影片场次ID")
  private Long showId;

  @Schema(description = "场次区域ID")
  private Long showAreaId;

  @Schema(description = "场次价格（元）")
  private BigDecimal showPrice;

  @Schema(description = "影片结算价（元）")
  private BigDecimal userPrice;

}
