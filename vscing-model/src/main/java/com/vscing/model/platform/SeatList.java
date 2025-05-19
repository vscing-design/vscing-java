package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SeatList {

  @Schema(description = "区域id")
  private String areaId;

  @Schema(description = "座位id")
  private String seatId;

  @Schema(description = "座位名称")
  private String seatName;

}
