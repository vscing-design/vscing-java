package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * MerchantVipOrderListDto
 *
 * @author vscing
 * @date 2025/6/2 20:46
 */
@Data
public class MerchantVipOrderListDto {

  @Schema(description = "商户ID")
  private Long merchantId;

  @Schema(description = "开始订单日期")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startDate;

  @Schema(description = "结束订单日期")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endDate;

  @Schema(description = "平台订单号")
  private String orderSn;

  @Schema(description = "外部订单号")
  private String extOrderSn;

  @Schema(description = "订单状态 1 待付款 2 进行中 3 成功 4 失败")
  private Integer status;

  @Schema(description = "退款类型 1 未退款 2 已全部退 3 部分退款 4 仅标记退款 5 原路退款")
  private Integer refundStatus;

  @Schema(description = "充值手机号 - 卡密不需要")
  private String phone;

  @Schema(description = "商品名称")
  private String vipGoodsName;

  @Schema(description = "商品类型 1 直冲  2 卡密")
  private Integer vipGoodsType;

}
