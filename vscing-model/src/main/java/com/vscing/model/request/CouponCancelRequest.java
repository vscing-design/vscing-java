package com.vscing.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CouponCancelRequest
 *
 * @author vscing
 * @date 2025/3/23 17:30
 */
@Data
public class CouponCancelRequest {

  @NotNull(message = "ID不能为空")
  @Schema(description = "ID")
  private List<Long> ids;

  @NotNull(message = "作废原因不能为空")
  @Schema(description = "作废原因 1 全部 2 错误券作废 3 订单退款")
  private Integer reason;

  @Schema(description = "作废日期")
  private LocalDateTime cancelAt;

  @Schema(description = "作废操作人")
  private Long updatedBy;

}
