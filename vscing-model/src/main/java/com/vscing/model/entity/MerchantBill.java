package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * MerchantBill
 *
 * @author vscing
 * @date 2025/4/18 23:27
 */
@Getter
@Setter
public class MerchantBill extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "商户ID")
  private Long merchantId;

  @Schema(description = "平台订单号")
  private String platformOrderNo;

  @Schema(description = "外部订单号")
  private String externalOrderNo;

  @Schema(description = "变动金额（正值表示收入，负值表示支出）")
  private BigDecimal changeAmount;

  @Schema(description = "变动后的账户余额")
  private BigDecimal changeAfterBalance;

  @Schema(description = "商品类型 1 电影票")
  private Integer productType;

  @Schema(description = "变动类型 1 订单支付扣款 2 订单退款 3 账户充值 4 账户退款")
  private Integer changeType;

  @Schema(description = "状态 1 进行中 2 成功 3 失败")
  private Integer status;

}
