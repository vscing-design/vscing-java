package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/** 
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:50:54
*/
@Getter
@Setter
public class Order extends BaseEntity {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "供应商ID")
    private Long supplierId;

    @Schema(description = "影院ID")
    private Long cinemaId;

    @Schema(description = "影片ID")
    private Long movieId;

    @Schema(description = "场次ID")
    private Long showId;

    @Schema(description = "订单号")
    private String orderSn;

    @Schema(description = "订单状态 1 待付款 2 受理中 3 待出票 4 已出票 5 已取消 6 退款中")
    private Integer status;

    @Schema(description = "订单类型 1 用户下单 2 手动下单")
    private Integer orderType;

    @Schema(description = "下单平台 1 微信小程序 2 支付宝小程序 3 云闪付小程序")
    private Integer platform;

    @Schema(description = "购票张数")
    private Integer purchaseQuantity;

    @Schema(description = "购票座位")
    private String seatInfo;

    @Schema(description = "是否调座 1 是 2 否")
    private Integer isAdjusted;

    @Schema(description = "订单总价格")
    private BigDecimal totalPrice;

    @Schema(description = "订单官方价")
    private BigDecimal officialPrice;

    @Schema(description = "订单结算价")
    private BigDecimal settlementPrice;

}
