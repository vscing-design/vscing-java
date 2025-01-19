package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * OrderApiPaymentVo
 *
 * @author vscing
 * @date 2025/1/19 13:20
 */
@Data
public class OrderApiPaymentVo {

  @Schema(description = "微信支付 时间戳")
  private String timeStamp;

  @Schema(description = "微信支付 随机字符串")
  private String nonceStr;

  @Schema(description = "微信支付 统一下单接口返回的 prepay_id 参数值")
  private String packageStr;

  @Schema(description = "微信支付 签名算法")
  private String signType;

  @Schema(description = "微信支付 签名")
  private String paySign;

  @Schema(description = "支付宝 交易号")
  private String tradeNo;

}
