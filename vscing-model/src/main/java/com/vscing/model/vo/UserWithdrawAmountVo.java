package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * UserWithdrawAmount
 *
 * @author vscing
 * @date 2025/3/2 21:30
 */
@Data
public class UserWithdrawAmountVo {

  @Schema(description = "状态 1 待审核 2 审核通过 3 审核不通过")
  private Integer status;

  @Schema(description = "提现金额(元)")
  private BigDecimal withdrawAmount;

}
