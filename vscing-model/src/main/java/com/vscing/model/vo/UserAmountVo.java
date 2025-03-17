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

  // 待提现金额：累计金额-已提现金额-审核中金额的值
  //已提现金额：通过提现申请的金额总和
  //审核中金额：已申请提现，但还未审核的金额总和
  //累计金额：用户获取的收益总和

  @Schema(description = "待提现金额(元)")
  private BigDecimal pendingAmount;

  @Schema(description = "已提现金额(元)")
  private BigDecimal withdrawnAmount;

  @Schema(description = "审核中金额(元)")
  private BigDecimal approveAmount;

  @Schema(description = "累计金额(元)")
  private BigDecimal totalAmount;

}
