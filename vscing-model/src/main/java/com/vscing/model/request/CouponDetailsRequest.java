package com.vscing.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * CouponDetailsRequest
 *
 * @author vscing
 * @date 2025/3/22 17:12
 */
@Data
public class CouponDetailsRequest {

  @NotNull(message = "签名不能为空")
  @Schema(description = "签名")
  private String sign;

  @NotNull(message = "手机号不能为空")
  @Pattern(regexp = "^1([3456789])\\d{9}$", message = "手机号格式不正确")
  @Schema(description = "手机号")
  private String phone;

  @NotNull(message = "优惠券码不能为空")
  @Schema(description = "优惠券码")
  private String code;

}
