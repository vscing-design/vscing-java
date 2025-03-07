package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * UserEarnApiInviteVo
 *
 * @author vscing
 * @date 2025/3/7 21:50
 */
@Data
public class UserEarnApiInviteVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "关联用户名称")
  private Long withUsername;

  @Schema(description = "关联用户手机号")
  private Long withPhone;

  @Schema(description = "更新时间")
  private LocalDateTime updatedAt;

}
