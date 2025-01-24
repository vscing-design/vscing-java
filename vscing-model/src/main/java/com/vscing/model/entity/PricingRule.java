package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * PricingRule
 *
 * @author vscing
 * @date 2024/12/29 23:22
 */
@Getter
@Setter
public class PricingRule extends BaseEntity {

  @Schema(description = "ID")
  private Long id;

  @Schema(description = "供应商ID")
  private Long supplierId;

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
