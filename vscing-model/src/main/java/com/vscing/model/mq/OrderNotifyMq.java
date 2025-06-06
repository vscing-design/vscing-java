package com.vscing.model.mq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * orderNotifyMq
 *
 * @author vscing
 * @date 2025/6/7 01:39
 */
@Data
public class OrderNotifyMq {

  @Schema(description = "通知url")
  private String url;

  @Schema(description = "订单号")
  private String orderNo;

  @Schema(description = "订单id")
  private Long orderId;

  @Schema(description = "订单类型 1 电影票 2 会员卡商品")
  private Integer orderType;

  @Schema(description = "5/10/15/20/25...分钟")
  private Integer num;

}
