package com.vscing.model.vo;

import com.vscing.model.entity.ShowArea;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ShowListVo
 *
 * @author vscing
 * @date 2024/12/29 00:23
 */
@Data
public class ShowListVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "影厅名称")
  private String hallName;

  @Schema(description = "影院ID")
  private Long cinemaId;

  @Schema(description = "影院名称")
  private Long cinemaName;

  @Schema(description = "影片ID")
  private Long movieId;

  @Schema(description = "影片名称")
  private Long movieName;

  @Schema(description = "放映开始时间")
  private LocalDateTime showTime;

  @Schema(description = "电影售卖结束时间")
  private LocalDateTime stopSellTime;

  @Schema(description = "放映时长（分钟）")
  private Integer duration;

  @Schema(description = "场次类型")
  private String showVersionType;

  @Schema(description = "场次价格（元）")
  private BigDecimal showPrice;

  @Schema(description = "影片结算价（元）")
  private BigDecimal userPrice;

  @Schema(description = "分区定价信息")
  private List<ShowArea> showAreaList;

}
