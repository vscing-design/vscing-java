package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * AdminVipOrderDto
 *
 * @author vscing
 * @date 2025/6/2 17:32
 */
@Data
public class AdminVipOrderDto {

  @Schema(description = "订单号")
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

  @Schema(description = "商品ID")
  private Long vipGoodsId;

  @Schema(description = "商户名称")
  private String merchantName;

  @Schema(description = "订单生成日期")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime createdAt;

}
