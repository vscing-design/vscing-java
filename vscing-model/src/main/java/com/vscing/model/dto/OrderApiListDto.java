package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * OrderApiListDto
 *
 * @author vscing
 * @date 2025/1/19 21:54
 */
@Data
public class OrderApiListDto {

  @Schema(description = "订单状态 多个以英文逗号分隔 1 待付款 2 待出票 3 出票中 4 已出票 5 已取消 6 退款中 7 退款完成 8 退款失败")
  private String status;

  @NotNull(message = "平台不能为空")
  @Schema(description = "平台 wechat 微信小程序 alipay 支付宝小程序 baidu 百度小程序")
  private String platform;

}
