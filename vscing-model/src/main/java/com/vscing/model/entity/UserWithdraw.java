package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * UserWithdraw
 *
 * @author vscing
 * @date 2025/3/2 16:48
 */
@Getter
@Setter
public class UserWithdraw extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "用户ID")
  private Long userId;

  @Schema(description = "申请提现渠道： 1 微信小程序  2 支付宝小程序  3 百度小程序")
  private Integer platform;

  @Schema(description = "申请提现金额(元)")
  private BigDecimal withdrawAmount;

  @Schema(description = "审核状态 1 待审核 2 审核通过 3 审核不通过")
  private Integer status;

  @Schema(description = "审核不通过原因")
  private String rejectReason;

  @Schema(description = "打款状态 1 打款中 2 打款成功 3 打款失败")
  private Integer withdrawStatus;

}
