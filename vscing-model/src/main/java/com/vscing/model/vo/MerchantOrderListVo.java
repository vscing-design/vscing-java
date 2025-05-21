package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * MerchantOrderListVo
 *
 * @author vscing
 * @date 2025/4/26 10:08
 */
@Data
public class MerchantOrderListVo {

  @Schema(description = "订单ID")
  private Long orderId;

  @Schema(description = "商户ID")
  private Long merchantId;

  @Schema(description = "商户名称")
  private String merchantName;

  @Schema(description = "创建时间")
  private LocalDateTime createdAt;

  @Schema(description = "订单状态 1 待付款 2 待出票 3 出票中 4 已出票 5 已取消 6 退款中 7 退款完成 8 退款失败")
  private Integer status;

  @Schema(description = "订单号")
  private String orderSn;

  @Schema(description = "外部订单号")
  private String extOrderSn;

  @Schema(description = "商品类型 1 电影票")
  private Integer productType;

  @Schema(description = "商品信息")
  private String movieName;

  @Schema(description = "商品份数")
  private Integer purchaseQuantity;

  @Schema(description = "商品单价")
  private BigDecimal productPrice;

  @Schema(description = "订单金额")
  private BigDecimal totalPrice;

  @Schema(description = "下单手机号")
  private String phone;

  @Schema(description = "省份名称")
  private String provinceName;

  @Schema(description = "城市名称")
  private String cityName;

  @Schema(description = "区县名称")
  private String districtName;

  @Schema(description = "详情地址")
  private String address;

  @Schema(description = "影厅名称")
  private String hallName;

  @Schema(description = "购票座位")
  private String seatInfo;

  @Schema(description = "商品码")
  private String ticketCode;

}
