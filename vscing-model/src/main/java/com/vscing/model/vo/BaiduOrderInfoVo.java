package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * BaiduOrderInfoVo
 *
 * @author vscing
 * @date 2025/2/21 22:26
 */
@Data
public class BaiduOrderInfoVo {

  @Schema(description = "dealId")
  private String dealId;

  @Schema(description = "appKey")
  private String appKey;

  @Schema(description = "订单金额（单位：人民币分）注：小程序测试包测试金额不可超过 1000 分")
  private String totalAmount;

  @Schema(description = "订单号")
  private String tpOrderId;

  @Schema(description = "订单名称")
  private String dealTitle;

  @Schema(description = "验签字段范围 默认1")
  private String signFieldsRange;

  @Schema(description = "进行 RSA 加密后的签名")
  private String rsaSign;

}
