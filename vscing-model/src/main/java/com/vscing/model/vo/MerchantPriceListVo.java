package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * MerchantPriceListVo
 *
 * @author vscing
 * @date 2025/4/19 16:18
 */
@Data
public class MerchantPriceListVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "商户id")
  private String merchantId;

  @Schema(description = "商户名称")
  private String merchantName;

  @Schema(description = "状态：1 合作中 2 已暂停")
  private Integer status;

  @Schema(description = "加价幅度")
  private BigDecimal markupAmount;

}
