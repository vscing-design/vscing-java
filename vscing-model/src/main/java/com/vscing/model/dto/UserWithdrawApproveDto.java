package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * UserWithdrawApproveDto
 *
 * @author vscing
 * @date 2025/3/2 20:58
 */
@Data
public class UserWithdrawApproveDto {

  @NotNull(message = "主键ID不能为空")
  @Schema(description = "主键ID")
  private Long id;

  @NotNull(message = "审核状态不能为空")
  @Schema(description = "审核状态 1 待审核 2 审核通过 3 审核不通过")
  private Integer status;

  @Schema(description = "审核不通过原因")
  private String rejectReason;

  @Schema(description = "打款状态 1 打款中 2 打款成功 3 打款失败")
  private Integer withdrawStatus;

  @Schema(description = "更新者ID")
  private Long updatedBy;

}
