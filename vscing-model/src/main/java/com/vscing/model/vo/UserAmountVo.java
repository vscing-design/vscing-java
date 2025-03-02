package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * UserAmountVo
 *
 * @author vscing
 * @date 2025/3/2 18:00
 */
@Data
public class UserAmountVo {

  @Schema(description = "待提现金额(元)")
  private BigDecimal pendingAmount;

  @Schema(description = "已提现金额(元)")
  private BigDecimal withdrawnAmount;

  @Schema(description = "累计佣金(元)")
  private BigDecimal totalAmount;

}
