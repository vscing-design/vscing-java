package com.vscing.model.mq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * RebateMq
 *
 * @author vscing
 * @date 2025/3/9 01:37
 */
@Data
public class RebateMq {

  @Schema(description = "当前用户id")
  private Long userId;

  @Schema(description = "订单id")
  private Long orderId;

}
