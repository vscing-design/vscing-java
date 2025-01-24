package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * MinPriceVo
 *
 * @author vscing
 * @date 2025/1/16 22:12
 */
@Data
public class MinPriceVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "影院ID")
  private Long cinemaId;

  @Schema(description = "影片ID")
  private Long movieId;

  @Schema(description = "影片场次ID")
  private Long showId;

  @Schema(description = "区域ID,可能为字母")
  private String area;

  @Schema(description = "电影售卖结束时间")
  private LocalDateTime stopSellTime;

  @Schema(description = "场次价格（元）")
  private BigDecimal showPrice;

  @Schema(description = "影片结算价（元）")
  private BigDecimal userPrice;



}
