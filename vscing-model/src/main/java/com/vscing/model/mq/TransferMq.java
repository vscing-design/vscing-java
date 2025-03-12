package com.vscing.model.mq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * transferMq
 *
 * @author vscing
 * @date 2025/3/13 00:26
 */
@Data
public class TransferMq {

  @Schema(description = "转账用户id")
  private Long userId;

  @Schema(description = "转账平台")
  private Integer platform;

  @Schema(description = "转账金额")
  private BigDecimal amount;

}
