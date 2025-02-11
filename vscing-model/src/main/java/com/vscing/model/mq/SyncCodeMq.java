package com.vscing.model.mq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * SyncCodeMq 同步取票码消息对象
 *
 * @author vscing
 * @date 2025/2/11 22:13
 */
@Data
public class SyncCodeMq {

  @Schema(description = "订单id")
  private Long orderId;

  @Schema(description = "查询次数")
  private int num;

}
