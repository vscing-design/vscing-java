package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * MerchantPrice
 *
 * @author vscing
 * @date 2025/4/18 23:20
 */
@Getter
@Setter
public class MerchantPrice extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "供应商ID")
  private Long supplierId;

  @Schema(description = "商户ID")
  private Long merchantId;

  @Schema(description = "加价幅度")
  private BigDecimal markupAmount;

}
