package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * SeatVo
 *
 * @author vscing
 * @date 2024/12/29 22:42
 */
@Slf4j
@Data
public class SeatVo {

  @Schema(description = "纵坐标")
  private String columnNo;

  @Schema(description = "横坐标")
  private String rowNo;

  @Schema(description = "座位名称")
  private String seatNo;

  @Schema(description = "第几排")
  private String pai;

  @Schema(description = "第几座")
  private String lie;

  @Schema(description = "状态 N可售，LK不可售")
  private String status;

  @Schema(description = "0为非情侣座 1为情侣座左 2为情侣座右")
  private Integer loveStatus;

  @Schema(description = "座位id")
  private String seatId;

  @Schema(description = "区域id,如不支持分区定价，则为空（null）；注意：当返回0时，表示支持分区定价。")
  private String areaId;

  public void parseAndSetPaiLie() {
    if (this.seatNo == null || this.seatNo.isEmpty()) {
      return;
    }
    // 假设 seatNo 格式为 "X排Y列"
    String[] parts = this.seatNo.split("排|座");
    if (parts.length == 2) {
      try {
        this.setPai(parts[0].trim());
        this.setLie(parts[1].trim());
      } catch (NumberFormatException e) {
        log.error("Invalid seatNo format: " + this.seatNo, e);
      }
    } else {
      log.error("Invalid seatNo format: " + this.seatNo);
    }
  }

}
