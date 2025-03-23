package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * CouponApiListDto
 *
 * @author vscing
 * @date 2025/3/23 17:46
 */
@Data
public class CouponApiListDto {

  @Schema(description = "用户id")
  private Long userId;

  @Schema(description = "优惠券状态类型 1 优惠券 2 历史优惠券")
  private Integer statusType;

}
