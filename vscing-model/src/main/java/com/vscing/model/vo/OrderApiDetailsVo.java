package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * OrderApiDetailsVo
 *
 * @author vscing
 * @date 2025/1/19 12:25
 */
@Data
public class OrderApiDetailsVo {

  @Schema(description = "场次id")
  private Long showId;

  @Schema(description = "海报图片")
  private String posterUrl;

  @Schema(description = "影片名称")
  private String movieName;

  @Schema(description = "放映开始时间")
  private LocalDateTime showTime;

  @Schema(description = "场次类型")
  private String showVersionType;

  @Schema(description = "电影售卖结束时间")
  private LocalDateTime stopSellTime;

  @Schema(description = "影院名称")
  private String cinemaName;

  @Schema(description = "影厅名称")
  private String hallName;

  @Schema(description = "原价")
  private BigDecimal maxPrice;

  @Schema(description = "临时值")
  private BigDecimal showPrice;

  @Schema(description = "临时值")
  private BigDecimal userPrice;

  @Schema(description = "售价")
  private BigDecimal minPrice;

  @Schema(description = "优惠金额")
  private BigDecimal discount;

  @Schema(description = "座位列表，票数取列表长度")
  private List<OrderApiSeatListVo> seatList;

}
