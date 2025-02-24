package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * BaiduRefundNotifyDto
 *
 * @author vscing
 * @date 2025/2/25 00:30
 */
@Data
public class BaiduRefundNotifyDto {

  @Schema(description = "百度收银台用户 ID")
  private Long userId;

  @Schema(description = "百度平台订单 ID")
  private Long orderId;

  @Schema(description = "开发者订单ID")
  private String tpOrderId;

  @Schema(description = "平台退款批次号")
  private String refundBatchId;

  @Schema(description = "退款状态 1：退款成功，2：退款失败 ")
  private Integer refundStatus;

  @Schema(description = "签名")
  private String rsaSign;

}
