package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * UserWithdrawListDto
 *
 * @author vscing
 * @date 2025/3/2 20:54
 */
@Data
public class UserWithdrawListDto {

  @Schema(description = "手机号")
  private String phone;

  @Schema(description = "审核状态：0 全部 1 待审核 2 审核通过 3 审核不通过")
  private Integer status;

  @Schema(description = "申请提现渠道：0 全部 1 微信小程序  2 支付宝小程序  3 百度小程序")
  private Integer platform;

}
