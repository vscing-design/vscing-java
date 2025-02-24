package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * BaiduCreateNotifyDto
 *
 * @author vscing
 * @date 2025/1/18 02:32
 */
@Data
public class BaiduCreateNotifyDto {

  @Schema(description = "百度收银台用户 ID")
  private Long userId;

  @Schema(description = "百度平台订单 ID")
  private Long orderId;

  @Schema(description = "单价 单位：分")
  private Integer unitPrice;

  @Schema(description = "数量")
  private Integer count;

  @Schema(description = "总金额 订单的实际金额（单位：分）")
  private Integer totalMoney;

  @Schema(description = "实付金额 扣除各种优惠后用户还需要支付的金额（单位：分）")
  private Integer payMoney;

  @Schema(description = "营销金额")
  private Integer promoMoney;

  @Schema(description = "红包支付金额")
  private Integer hbMoney;

  @Schema(description = "余额支付金额")
  private Integer hbBalanceMoney;

  @Schema(description = "抵用券金额")
  private Integer giftCardMoney;

  @Schema(description = "百度收银台凭证")
  private Long dealId;

  @Schema(description = "支付时间")
  private Integer payTime;

  @Schema(description = "促销详情")
  private Object promoDetail;

  @Schema(description = "支付渠道 1087：支付宝；1108：百度钱包；1117：微信；1124：花呗；10001：银联对公支付；10002：银联对私支付；10003：汇付天下；10004：百度闪付")
  private Integer payType;

  @Schema(description = "支付平台")
  private Integer partnerId;

  @Schema(description = "订单支付状态")
  private Integer status;

  @Schema(description = "开发者订单ID")
  private String tpOrderId;

  @Schema(description = "开发者透传数据")
  private Object returnData;

  @Schema(description = "签名")
  private String rsaSign;

}
