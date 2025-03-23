package com.vscing.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
  private Long id;

  @NotNull(message = "作废原因不能为空")
  @Schema(description = "作废原因 1 全部 2 错误券作废 3 订单退款")
  private Integer reason;

}
