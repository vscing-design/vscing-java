package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * OrderDetail
 *
 * @author vscing
 * @date 2025/1/2 16:26
 */
@Getter
@Setter
public class OrderDetail extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "订单ID")
  private Long orderId;

  @Schema(description = "区域ID")
  private String tpAreaId;

  @Schema(description = "座位ID")
  private String tpSeatId;

  @Schema(description = "座位名")
  private String tpSeatName;

  @Schema(description = "取票类型")
  private String ticketType;

  @Schema(description = "取票码")
  private String ticketCode;

  @Schema(description = "总价格")
  private BigDecimal totalPrice;

  @Schema(description = "官方价")
  private BigDecimal officialPrice;

  @Schema(description = "结算价")
  private BigDecimal settlementPrice;



}
