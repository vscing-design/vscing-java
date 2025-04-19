package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * MerchantBank
 *
 * @author vscing
 * @date 2025/4/18 23:25
 */
@Getter
@Setter
public class MerchantBank extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "商户ID")
  private Long merchantId;

  @Schema(description = "银行名称")
  private String bankName;

  @Schema(description = "开户支行名称")
  private String branchName;

  @Schema(description = "银行账户")
  private String bankAccount;

  @Schema(description = "联系人")
  private String contacts;

  @Schema(description = "预留手机号")
  private String phone;

}
