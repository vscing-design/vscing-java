package com.vscing.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * InitiateWithdrawRequest
 *
 * @author vscing
 * @date 2025/1/5 18:27
 */
@Data
public class InitiateWithdrawRequest {

  @NotNull(message = "平台不能为空")
  @Schema(description = "平台 wechat 微信小程序 alipay 支付宝小程序 其他平台不支持")
  private String platform;

  @NotNull(message = "提现金额不能为空")
  @DecimalMin(value = "10", message = "提现金额不能小于10")
  @Schema(description = "提现金额")
  private BigDecimal withdrawAmount;

}
