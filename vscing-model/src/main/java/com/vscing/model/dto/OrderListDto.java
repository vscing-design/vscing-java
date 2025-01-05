package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * OrderListDto
 *
 * @author vscing
 * @date 2024/12/31 01:13
 */
@Data
public class OrderListDto {

  @Schema(description = "影院名称")
  private String cinemaName;

  @Schema(description = "影片名称")
  private String movieName;

  @Schema(description = "下单用户")
  private String username;

  @Schema(description = "订单号")
  private String orderSn;

  @Schema(description = "订单状态 多个以英文逗号分隔 1 待付款 2 待出票 3 出票中 4 已出票 5 已取消 6 退款中 7 退款完成")
  private String status;

  @Schema(description = "订单类型 1 用户下单 2 手动下单")
  private Integer orderType;

  @Schema(description = "下单平台 1 微信小程序 2 支付宝小程序 3 淘宝 4 咸鱼 5 拼多多 6 微信")
  private Integer platform;

  @Schema(description = "订单生成日期")
  private LocalDateTime createdAt;

}
