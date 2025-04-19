package com.vscing.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * MerchantRefundRequest
 *
 * @author vscing
 * @date 2025/4/19 16:40
 */
@Data
public class MerchantRefundRequest {

  @NotNull(message = "主键ID不能为空")
  @Schema(description = "主键ID")
  private Long id;

  @NotNull(message = "退款金额不能为空")
  @Schema(description = "退款金额")
  private BigDecimal refundAmount;

  @NotNull(message = "图片凭证不能为空")
  @Schema(description = "图片凭证")
  private String pictureVoucher;

  @NotNull(message = "备注不能为空")
  @Schema(description = "备注")
  private String remark;

  @Schema(description = "操作人")
  private Long updatedBy;

}
