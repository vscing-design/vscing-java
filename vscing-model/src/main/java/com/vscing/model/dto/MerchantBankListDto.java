package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * MerchantBankListDto
 *
 * @author vscing
 * @date 2025/4/19 10:11
 */
@Data
public class MerchantBankListDto {

  @Schema(description = "商户ID")
  private Long merchantId;

}
