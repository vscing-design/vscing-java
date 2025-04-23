package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * MerchantBillRechargeListDto
 *
 * @author vscing
 * @date 2025/4/23 21:20
 */
@Data
public class MerchantBillRechargeListDto {

  @Schema(description = "商户名称")
  private String merchantName;

  @Schema(description = "状态 1 进行中(待审核) 2 成功(审核通过) 3 失败(已关闭)")
  private Integer status;

}
