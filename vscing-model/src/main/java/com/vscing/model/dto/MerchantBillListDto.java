package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * MerchantBillListDto
 *
 * @author vscing
 * @date 2025/4/19 16:00
 */
@Data
public class MerchantBillListDto {

  @Schema(description = "商户ID")
  private Long merchantId;

  @Schema(description = "开始变动日期")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startDate;

  @Schema(description = "结束变动日期")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endDate;

  @Schema(description = "平台订单号")
  private String platformOrderNo;

  @Schema(description = "外部订单号")
  private String externalOrderNo;

  @Schema(description = "变动类型 1 订单支付扣款 2 订单退款 3 账户充值 4 账户退款")
  private Integer changeType;

  @Schema(description = "状态 1 进行中 2 成功 3 失败")
  private Integer status;

}
