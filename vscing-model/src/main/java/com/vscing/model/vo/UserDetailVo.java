package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * UserDetailVo
 *
 * @author vscing
 * @date 2025/1/6 11:51
 */
@Data
public class UserDetailVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "上级用户ID")
  private Long firstUserId;

  @Schema(description = "用户名称")
  private String username;

  @Schema(description = "密码")
  private String password;

  @Schema(description = "头像")
  private String avatar;

  @Schema(description = "用户手机号")
  private String phone;

  @Schema(description = "待提现金额")
  private BigDecimal pendingAmount;

  @Schema(description = "已提现金额")
  private BigDecimal withdrawnAmount;

  @Schema(description = "累计佣金")
  private BigDecimal totalAmount;

  @Schema(description = "帐号启用状态：1->启用 2->禁用")
  private Integer status;

  @Schema(description = "最后登陆IP")
  private String lastIp;

  @Schema(description = "最后登陆时间")
  private LocalDateTime loginAt;

  @Schema(description = "创建时间")
  private LocalDateTime createdAt;

  @Schema(description = "创建者ID")
  private Long createdBy;

  @Schema(description = "更新时间")
  private LocalDateTime updatedAt;

  @Schema(description = "更新者ID")
  private Long updatedBy;

}
