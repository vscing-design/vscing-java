package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * MerchantOrderCountVo
 *
 * @author vscing
 * @date 2025/4/26 09:47
 */
@Data
public class MerchantVipOrderCountVo {

  @Schema(description = "订单日期")
  private LocalDateTime orderDate;

  @Schema(description = "订单数量")
  private Integer orderQuantity;

  @Schema(description = "订单金额")
  private BigDecimal orderAmount;

}
