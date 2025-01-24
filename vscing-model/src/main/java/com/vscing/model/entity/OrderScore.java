package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * ShowScore
 *
 * @author vscing
 * @date 2025/1/23 01:32
 */
@Getter
@Setter
public class OrderScore extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "订单ID")
  private Long orderId;

  @Schema(description = "用户ID")
  private Long userId;

  @Schema(description = "评分")
  private Integer score;

}
