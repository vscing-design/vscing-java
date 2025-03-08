package com.vscing.model.mq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * InviteMq
 *
 * @author vscing
 * @date 2025/3/8 20:42
 */
@Data
public class InviteMq {

  @Schema(description = "当前用户id")
  private Long userId;

  @Schema(description = "邀请用户id")
  private Long inviteUserId;

}
