package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * seatListDto
 *
 * @author vscing
 * @date 2024/12/31 01:44
 */
@Data
public class SeatListDto {

  @NotNull(message = "座位ID不能为空")
  @Schema(description = "座位ID")
  private Long seatId;

  @NotNull(message = "座位名称不能为空")
  @Schema(description = "座位名称")
  private String seatName;

}
