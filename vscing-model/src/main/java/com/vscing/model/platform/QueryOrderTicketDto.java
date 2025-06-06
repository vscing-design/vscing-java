package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryOrderTicketDto extends QueryDto {

  @Schema(description = "平台订单号，必须传一个")
  private String orderNo;

  @Schema(description = "商户唯一订单号，必须传一个")
  private String tradeNo;

}
