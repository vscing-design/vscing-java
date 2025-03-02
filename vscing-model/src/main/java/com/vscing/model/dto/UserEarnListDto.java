package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * UserEarnListDto
 *
 * @author vscing
 * @date 2025/3/2 23:25
 */
@Data
public class UserEarnListDto {

  @Schema(description = "推广类型：0 全部 1 邀请好友 2 电影票订单")
  private Integer earnType;

  @Schema(description = "推广用户手机号")
  private String phone;

  @Schema(description = "邀请好友手机号")
  private String withUserPhone;

  @Schema(description = "订单号")
  private String withOrderSn;

}
