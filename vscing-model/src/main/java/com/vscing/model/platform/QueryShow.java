package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class QueryShow {

  @Schema(description = "影片id")
  private Long movieId;

  @Schema(description = "影片名称")
  private String movieName;

  @Schema(description = "场次ID")
  private Long showId;

  @Schema(description = "影厅名称")
  private String hallName;

  @Schema(description = "放映时长（分钟）")
  private Integer duration;

  @Schema(description = "放映开始时间")
  private LocalDateTime showTime;

  @Schema(description = "电影售卖结束时间")
  private LocalDateTime stopSellTime;

  @Schema(description = "场次类型")
  private String showVersionType;

  @Schema(description = "场次价格（元）")
  private BigDecimal showPrice;

  @Schema(description = "影片结算价（元）")
  private BigDecimal userPrice;

  @Schema(description = "场次区域列表")
  private List<QueryShowArea> showAreaList;

}
