package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuerySubmitVipOrderDto extends QueryDto {

  @NotNull(message = "商品ID未传入")
  @Schema(description = "商品ID")
  private Long goodsId;

  @NotNull(message = "购买数量未传入")
  @Schema(description = "购买数量")
  private Integer buyNum;

  @NotNull(message = "商户平台订单号未传入")
  @Schema(description = "商户平台订单号")
  private String tradeNo;

  @NotNull(message = "充值手机号未传入")
  @Schema(description = "充值手机号(卡密订单无需传递)")
  private String phone;

  @Schema(description = "异步回调地址")
  private String notifyUrl;

  @Schema(description = "最大进货总金额（非商品单价），该验证防止商家亏损。原理：商家进货价格<=此值放行，>此值则拦截")
  private BigDecimal maxMoney;

}
