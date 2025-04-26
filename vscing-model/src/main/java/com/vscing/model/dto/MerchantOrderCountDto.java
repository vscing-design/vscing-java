package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * MerchantOrderCountDto
 *
 * @author vscing
 * @date 2025/4/26 10:03
 */
@Data
public class MerchantOrderCountDto {

  @Schema(description = "商户ID")
  private Long merchantId;

  @Schema(description = "开始订单日期")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startOrderDate;

  @Schema(description = "结束订单日期")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endOrderDate;

  @Schema(description = "商品类型 1 电影票")
  private Integer productType;

}
