package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * ShowInforDto
 *
 * @author vscing
 * @date 2025/1/2 20:56
 */
@Data
public class ShowInforDto {

  @NotNull(message = "座位ID不能为空")
  @Schema(description = "座位ID")
  private String seatId;

  @NotNull(message = "座位名称不能为空")
  @Schema(description = "座位名称")
  private String seatName;

}
