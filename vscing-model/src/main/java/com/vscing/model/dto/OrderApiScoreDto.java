package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * OrderApiScoreDto
 *
 * @author vscing
 * @date 2025/1/23 01:36
 */
@Data
public class OrderApiScoreDto {

  @NotNull(message = "订单ID不能为空")
  @Schema(description = "订单id")
  private Long id;

  @NotNull(message = "评分不能为空")
  @Schema(description = "评分")
  private Integer score;

}
