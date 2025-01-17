package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * OrderApiListVo
 *
 * @author vscing
 * @date 2025/1/19 21:55
 */
@Data
public class OrderApiListVo {

  @Schema(description = "订单id")
  private Long id;

  @Schema(description = "影院名称")
  private String cinemaName;

  @Schema(description = "海报图片")
  private String posterUrl;

  @Schema(description = "影片名称")
  private String movieName;

  @Schema(description = "影厅名称")
  private String hallName;

  @Schema(description = "购票张数")
  private Integer purchaseQuantity;

  @Schema(description = "订单总价格")
  private BigDecimal totalPrice;

  @Schema(description = "放映开始时间")
  private LocalDateTime showTime;

  @Schema(description = "创建时间")
  private LocalDateTime createdAt;

}
