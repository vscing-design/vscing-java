package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * MerchantDetailVo
 *
 * @author vscing
 * @date 2025/4/18 23:01
 */
@Data
public class MerchantDetailVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "商户名称")
  private String merchantName;

  @Schema(description = "密码")
  private String password;

  @Schema(description = "状态：1 合作中 2 已暂停")
  private Integer status;

}
