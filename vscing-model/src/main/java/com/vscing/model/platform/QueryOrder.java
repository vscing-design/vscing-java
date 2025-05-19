package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QueryOrder {

  @Schema(description = "平台订单号")
  private String orderNo;

  @Schema(description = "商户唯一订单号")
  private String tradeNo;

  @Schema(description = "下单时间")
  private LocalDateTime orderTime;

  @Schema(description = "取票手机号")
  private String phoneNumber;

}
