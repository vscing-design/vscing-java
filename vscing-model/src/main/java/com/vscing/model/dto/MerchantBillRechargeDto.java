package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * MerchantBillRechargeDto
 *
 * @author vscing
 * @date 2025/4/26 00:42
 */
@Data
public class MerchantBillRechargeDto {

  @Schema(description = "商户ID")
  private Long merchantId;

  @NotNull(message = "对公户ID不能为空")
  @Schema(description = "对公户ID")
  private Long bankId;

  @NotNull(message = "开户支行名称不能为空")
  @Schema(description = "开户支行名称")
  private String branchName;

  @NotNull(message = "银行账户不能为空")
  @Schema(description = "银行账户")
  private String bankAccount;

  @NotNull(message = "充值金额不能为空")
  @Schema(description = "充值金额")
  private BigDecimal changeAmount;

}
