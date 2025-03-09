package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * UserEarnApiInviteNoticeVo
 *
 * @author vscing
 * @date 2025/3/9 21:31
 */
@Data
public class UserEarnApiInviteNoticeVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "关联用户名称")
  private String withUsername;

  @Schema(description = "推广类型：1 邀请好友 2 电影票订单")
  private Integer earnType;

  @Schema(description = "推广金额")
  private BigDecimal earnAmount;

  @Schema(description = "更新时间")
  private LocalDateTime updatedAt;

}
