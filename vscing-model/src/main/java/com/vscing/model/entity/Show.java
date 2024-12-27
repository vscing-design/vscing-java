package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * MovieShow
 *
 * @author vscing
 * @date 2024/12/27 21:10
 */
@Getter
@Setter
public class Show extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "三方ID")
  private Long tpShowId;

  @Schema(description = "影院ID")
  private Long cinemaId;

  @Schema(description = "影片ID")
  private Long movieId;

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

}
