package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * MerchantOrderListDto
 *
 * @author vscing
 * @date 2025/4/26 10:06
 */
@Data
public class MerchantOrderListDto {

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

  @Schema(description = "下单手机号")
  private String phone;

  @Schema(description = "商品类型 1 电影票 2 会员")
  private Integer productType;

  @Schema(description = "订单状态 1 待付款 2 待出票 3 出票中 4 已出票 5 已取消 6 退款中 7 退款完成 8 退款失败")
  private Integer status;

}
