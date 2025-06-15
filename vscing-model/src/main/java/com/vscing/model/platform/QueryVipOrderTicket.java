package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class QueryVipOrderTicket {

  @Schema(description = "平台订单号")
  private String orderNo;

  @Schema(description = "商户唯一订单号")
  private String tradeNo;

  @Schema(description = "下单时间")
  private LocalDateTime orderTime;

  @Schema(description = "充值手机号")
  private String phone;

  @Schema(description = "订单状态 1 待付款 2 进行中 3 成功 4 失败")
  private Integer status;

  @Schema(description = "退款类型 1 未退款 2 已全部退 3 部分退款 4 仅标记退款 5 原路退款")
  private Integer refundStatus;

  @Schema(description = "订单总金额")
  private BigDecimal money;

  @Schema(description = "最大进货总金额（非商品单价），该验证防止商家亏损。原理：商家进货价格<=此值放行，>此值则拦截")
  private BigDecimal maxMoney;

  @Schema(description = "退款金额")
  private BigDecimal refundMoney;

  @Schema(description = "订单回执")
  private String receipt;

  @Schema(description = "退款回执")
  private String refundReceipt;

  @Schema(description = "卡密信息json数组")
  private String cardList;

}
