package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * UserWithdrawListVo
 *
 * @author vscing
 * @date 2025/3/2 21:30
 */
@Data
public class UserWithdrawListVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "用户ID")
  private Long userId;

  @Schema(description = "用户手机号")
  private String phone;

  @Schema(description = "申请提现渠道： 1 微信小程序  2 支付宝小程序  3 百度小程序")
  private Integer platform;

  @Schema(description = "申请提现金额(元)")
  private BigDecimal withdrawAmount;

  @Schema(description = "审核状态 1 待审核 2 审核通过 3 审核不通过")
  private Integer status;

  @Schema(description = "审核不通过原因")
  private String rejectReason;

  @Schema(description = "申请时间")
  private LocalDateTime createdAt;

  @Schema(description = "更新时间")
  private LocalDateTime updatedAt;

}
