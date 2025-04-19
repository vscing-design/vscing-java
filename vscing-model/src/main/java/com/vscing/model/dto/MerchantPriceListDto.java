package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * MerchantPriceListDto
 *
 * @author vscing
 * @date 2025/4/19 09:59
 */
@Data
public class MerchantPriceListDto {

  @Schema(description = "商户名称")
  private String merchantName;

  @Schema(description = "商户状态：0 全部 1 合作中 2 已暂停")
  private Integer status;

}
