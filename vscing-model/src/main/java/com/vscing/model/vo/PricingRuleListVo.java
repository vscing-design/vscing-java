package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * PricingRuleListVo
 *
 * @author vscing
 * @date 2024/12/29 23:23
 */
@Data
public class PricingRuleListVo {

  @Schema(description = "ID")
  private Long id;

  @Schema(description = "差价下限(NULL 表示无下限)")
  private BigDecimal minDiff;

  @Schema(description = "运算符 1:  >=  2:  >  3: <=  4: <")
  private Integer minOperator;

  @Schema(description = "差价上限(NULL 表示无上限)")
  private BigDecimal maxDiff;

  @Schema(description = "运算符 1:  >=  2:  >  3: <=  4: <")
  private Integer maxOperator;

  @Schema(description = "加价金额")
  private BigDecimal markupAmount;

}
