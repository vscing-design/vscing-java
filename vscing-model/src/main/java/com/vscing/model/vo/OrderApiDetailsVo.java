package com.vscing.model.vo;

import com.vscing.model.entity.OrderDetail;
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

  @Schema(description = "订单id")
  private Long id;

  @Schema(description = "订单状态 1 待付款 2 待出票 3 出票中 4 已出票 5 已取消 6 退款中 7 退款完成")
  private Integer status;

  @Schema(description = "购票张数")
  private Integer purchaseQuantity;

  @Schema(description = "购票座位")
  private String seatInfo;

  @Schema(description = "供应商取票码JSON字符串")
  private String ticketCode;

  @Schema(description = "下单手机号")
  private String phone;

  @Schema(description = "订单号")
  private String orderSn;

  @Schema(description = "实付金额")
  private BigDecimal totalPrice;

  @Schema(description = "原价")
  private BigDecimal officialPrice;

  @Schema(description = "平台优惠")
  private BigDecimal discount;

  @Schema(description = "创建时间")
  private LocalDateTime createdAt;

  @Schema(description = "影院名称")
  private String cinemaName;

  @Schema(description = "省份名称")
  private String provinceName;

  @Schema(description = "城市名称")
  private String cityName;

  @Schema(description = "区县名称")
  private String districtName;

  @Schema(description = "详细地址")
  private String address;

  @Schema(description = "经度")
  private Double lng;

  @Schema(description = "维度")
  private Double lat;

  @Schema(description = "影院联系方式")
  private String cinemaPhone;

  @Schema(description = "影片名称")
  private String movieName;

  @Schema(description = "海报图片")
  private String posterUrl;

  @Schema(description = "影厅名称")
  private String hallName;

  @Schema(description = "放映开始时间")
  private LocalDateTime showTime;

  @Schema(description = "放映时长（分钟）")
  private Integer duration;

  @Schema(description = "场次类型")
  private String showVersionType;

  @Schema(description = "电影售卖结束时间")
  private LocalDateTime stopSellTime;

//  @Schema(description = "座位列表，票数取列表长度")
//  private List<OrderApiSeatListVo> seatList;

  @Schema(description = "座位列表")
  private List<OrderDetail> orderDetailList;

  @Schema(description = "评分")
  private Integer score;

}
