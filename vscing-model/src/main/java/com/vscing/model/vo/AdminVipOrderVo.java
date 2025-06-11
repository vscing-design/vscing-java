package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * AdminVipOrderVo
 *
 * @author vscing
 * @date 2025/6/2 19:29
 */
@Data
public class AdminVipOrderVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "订单号")
  private String orderSn;

  @Schema(description = "外部订单号")
  private String extOrderSn;

  @Schema(description = "订单状态 1 待付款 2 进行中 3 成功 4 失败")
  private Integer status;

  @Schema(description = "退款类型 1 未退款 2 已全部退 3 部分退款 4 仅标记退款 5 原路退款")
  private Integer refundStatus;

  @Schema(description = "订单总价格")
  private BigDecimal totalPrice;

  @Schema(description = "订单市场价")
  private BigDecimal officialPrice;

  @Schema(description = "订单结算价")
  private BigDecimal settlementPrice;

  @Schema(description = "商户ID")
  private Long merchantId;

  @Schema(description = "商户名称")
  private String merchantName;

  @Schema(description = "商品ID")
  private Long vipGoodsId;

  @Schema(description = "充值手机号 - 卡密不需要")
  private String phone;

  @Schema(description = "购买数量")
  private Integer buyNum;

  @Schema(description = "卡密信息json数组")
  private String cardList;

  @Schema(description = "商品名称")
  private String vipGoodsName;

  @Schema(description = "商品类型 1 直冲  2 卡密")
  private Integer vipGoodsType;

  @Schema(description = "供应商ID")
  private Long supplierId;

  @Schema(description = "供应商订单号")
  private String supplierOrderSn;

  @Schema(description = "结算状态 1 已结算 2 未结算")
  private Integer settleStatus;

  @Schema(description = "创建时间")
  private LocalDateTime createdAt;

  @Schema(description = "更新时间")
  private LocalDateTime updatedAt;

}
