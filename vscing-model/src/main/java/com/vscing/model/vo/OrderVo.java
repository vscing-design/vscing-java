package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:52:31
 */
@Data
public class OrderVo {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名称")
    private String username;

    @Schema(description = "下单手机号")
    private String phone;

    @Schema(description = "供应商ID")
    private Long supplierId;

    @Schema(description = "供应商名称")
    private String supplierName;

    @Schema(description = "影院ID")
    private Long cinemaId;

    @Schema(description = "影院名称")
    private String cinemaName;

    @Schema(description = "省份ID")
    private Long provinceId;

    @Schema(description = "省份名称")
    private String provinceName;

    @Schema(description = "城市ID")
    private Long cityId;

    @Schema(description = "城市名称")
    private String cityName;

    @Schema(description = "区县ID")
    private Long districtId;

    @Schema(description = "区县名称")
    private String districtName;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "影片ID")
    private Long movieId;

    @Schema(description = "影片名称")
    private String movieName;

    @Schema(description = "影片语言")
    private String language;

    @Schema(description = "影片类型")
    private String movieType;

    @Schema(description = "放映开始时间")
    private LocalDateTime showTime;

    @Schema(description = "电影售卖结束时间")
    private LocalDateTime stopSellTime;

    @Schema(description = "放映时长（分钟）")
    private Integer duration;

    @Schema(description = "场次ID")
    private Long showId;

    @Schema(description = "影厅名称")
    private String hallName;

    @Schema(description = "订单号")
    private String orderSn;

    @Schema(description = "三方订单号")
    private String supplierOrderSn;

    @Schema(description = "取票码JSON数组，每组code数组的value以｜分隔组成二维码。需生成取票二维码的话，请按以下方式生成：\n" +
        "①序列号|取票码|验证码  \n" +
        "②取票码|验证码\n" +
        "③取票码\n" +
        "④验证码\n")
    private String ticketCode;

    @Schema(description = "订单状态 1 待付款 2 待出票 3 出票中 4 已出票 5 已取消 6 退款中 7 退款完成")
    private Integer status;

    @Schema(description = "订单类型 1 用户下单 2 手动下单")
    private Integer orderType;

    @Schema(description = "下单平台 1 微信小程序 2 支付宝小程序 3 淘宝 4 咸鱼 5 拼多多 6 微信")
    private Integer platform;

    @Schema(description = "用户支付状态 1 已支付 2 未支付")
    private Integer paymentStatus;

    @Schema(description = "供应商结算状态 1 已结算 2 未结算")
    private Integer settleStatus;

    @Schema(description = "购票张数")
    private Integer purchaseQuantity;

    @Schema(description = "购票座位")
    private String seatInfo;

    @Schema(description = "无座位时，接受系统调座 1 是 2 否")
    private Integer seatAdjusted;

    @Schema(description = "是否调座 1 是 2 否")
    private Integer isAdjusted;

    @Schema(description = "订单总价格")
    private BigDecimal totalPrice;

    @Schema(description = "订单官方价")
    private BigDecimal officialPrice;

    @Schema(description = "订单结算价")
    private BigDecimal settlementPrice;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

}
