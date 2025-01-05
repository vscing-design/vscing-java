package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ShowTreeVo
 *
 * @author vscing
 * @date 2025/1/1 14:13
 */
@Data
public class ShowTreeVo {

  @Schema(description = "供应商ID")
  private Long supplierId;

  @Schema(description = "影院ID")
  private Long cinemaId;

  @Schema(description = "影片ID")
  private Long movieId;

  @Schema(description = "影片名称")
  private String movieName;

  @Schema(description = "场次ID")
  private Long showId;

  @Schema(description = "影厅名称")
  private String hallName;

  @Schema(description = "放映开始时间")
  private LocalDateTime showTime;

  @Schema(description = "电影售卖结束时间")
  private LocalDateTime stopSellTime;

  @Schema(description = "放映时长（分钟）")
  private Integer duration;

  @Schema(description = "场次类型")
  private String showVersionType;

}
