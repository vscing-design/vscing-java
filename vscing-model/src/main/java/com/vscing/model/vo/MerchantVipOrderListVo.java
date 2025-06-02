package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * MerchantVipOrderListVo
 *
 * @author vscing
 * @date 2025/6/2 20:57
 */
@Data
public class MerchantVipOrderListVo {

  @Schema(description = "订单ID")
  private Long id;

  @Schema(description = "订单创建时间")
  private LocalDateTime createdAt;

  @Schema(description = "订单状态 1 待付款 2 进行中 3 成功 4 失败")
  private Integer status;

  @Schema(description = "退款状态 1 未退款 2 已全部退 3 部分退款 4 仅标记退款 5 原路退款")
  private Integer refundStatus;

  @Schema(description = "商户名称")
  private String merchantName;

  @Schema(description = "平台订单号")
  private String orderSn;

  @Schema(description = "外部订单号")
  private String extOrderSn;

  @Schema(description = "商品类型 1 直冲  2 卡密")
  private Integer vipGoodsType;

  @Schema(description = "商品名称")
  private String vipGoodsName;

  @Schema(description = "商品份数")
  private Integer buyNum;

  @Schema(description = "商品单价")
  private BigDecimal unitPrice;

  @Schema(description = "订单总价格")
  private BigDecimal totalPrice;

  @Schema(description = "充值手机号 - 卡密不需要")
  private String phone;

  @Schema(description = "卡密信息json数组")
  private String cardList;

}
