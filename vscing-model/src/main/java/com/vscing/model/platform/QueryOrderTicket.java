package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class QueryOrderTicket {

  @Schema(description = "平台订单号")
  private String orderNo;

  @Schema(description = "商户唯一订单号")
  private String tradeNo;

  @Schema(description = "下单时间")
  private LocalDateTime orderTime;

  @Schema(description = "取票手机号")
  private String phoneNumber;

  @Schema(description = "订单总金额")
  private BigDecimal amount;

  @Schema(description = "购票座位")
  private String seatInfo;

  @Schema(description = "取票码信息")
  private String ticketCodeStr;

  @Schema(description = "订单状态 1 待付款 2 待出票 3 出票中 4 已出票 5 已取消")
  private Integer orderStatus;

  @Schema(description = "影片名称")
  private String movieName;

  @Schema(description = "海报图片")
  private String posterUrl;

  @Schema(description = "影院名称")
  private String cinemaName;

  @Schema(description = "影院地址")
  private String cinemaAddress;

  @Schema(description = "影厅名称")
  private String hallName;

  @Schema(description = "放映时长（分钟）")
  private Integer duration;

  @Schema(description = "放映开始时间")
  private LocalDateTime showTime;

  @Schema(description = "场次类型")
  private String showVersionType;

}
