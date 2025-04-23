package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * MerchantBillRechargeListVo
 *
 * @author vscing
 * @date 2025/4/23 21:23
 */
@Data
public class MerchantBillRechargeListVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "商户ID")
  private Long merchantId;

  @Schema(description = "商户名称")
  private String merchantName;

  @Schema(description = "充值金额（正值表示收入，负值表示支出）")
  private BigDecimal changeAmount;

  @Schema(description = "充值状态 1 进行中(待审核) 2 成功(审核通过) 3 失败(已关闭)")
  private Integer status;

  @Schema(description = "申请充值时间")
  private LocalDateTime createdAt;

  @Schema(description = "开户支行名称")
  private String branchName;

  @Schema(description = "银行账户")
  private String bankAccount;

}
