package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * PricingRuleListDto
 *
 * @author vscing
 * @date 2024/12/29 23:22
 */
@Data
public class PricingRuleListDto {

  @Schema(description = "ID")
  private Long id;

  @Schema(description = "供应商ID")
  private Long supplierId;

}
