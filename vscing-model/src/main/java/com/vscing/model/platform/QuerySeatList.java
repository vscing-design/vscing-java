package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QuerySeatList {

  @Schema(description = "纵坐标")
  private String columnNo;

  @Schema(description = "横坐标")
  private String rowNo;

  @Schema(description = "座位名称")
  private String seatNo;

  @Schema(description = "状态 N可售，LK不可售")
  private String status;

  @Schema(description = "0为非情侣座 1为情侣座左 2为情侣座右")
  private Integer loveStatus;

  @Schema(description = "座位id")
  private String seatId;

  @Schema(description = "区域id,如不支持分区定价，则为空（null）；注意：当返回0时，表示支持分区定价。")
  private String areaId;

}
