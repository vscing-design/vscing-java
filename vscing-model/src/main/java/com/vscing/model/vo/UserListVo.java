package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * UserListVo
 *
 * @author vscing
 * @date 2024/12/29 00:02
 */
@Data
public class UserListVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "来源 1 微信小程序 2 支付宝小程序 3 百度小程序")
  private Integer source;

  @Schema(description = "用户名称")
  private String username;

  @Schema(description = "用户手机号/推广用户手机号")
  private String phone;

  @Schema(description = "用户订单数")
  private Integer orderNum;

  @Schema(description = "用户星级")
  private Integer star;

  @Schema(description = "待提现金额(元)")
  private BigDecimal pendingAmount;

  @Schema(description = "已提现金额(元)")
  private BigDecimal withdrawnAmount;

  @Schema(description = "累计佣金(元)")
  private BigDecimal totalAmount;

  @Schema(description = "用户入驻时间/创建时间")
  private LocalDateTime createdAt;

  @Schema(description = "更新时间")
  private LocalDateTime updatedAt;

}
