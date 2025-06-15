package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * MerchantListDto
 *
 * @author vscing
 * @date 2025/4/19 00:32
 */
@Data
public class MerchantListDto {

  @Schema(description = "商户名称")
  private String merchantName;

  @Schema(description = "商户状态：0 全部 1 合作中 2 已暂停")
  private Integer status;

  @Schema(description = "合作产品：1 电影票 2 会员卡商品")
  private List<Integer> productType;

}
