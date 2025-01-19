package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * OrderApiDetailsDto
 *
 * @author vscing
 * @date 2025/1/19 12:19
 */
@Data
public class OrderApiDetailsDto {

  @NotNull(message = "场次ID不能为空")
  @Schema(description = "场次id")
  private Long showId;

  @NotNull(message = "选中座位不能为空")
  @Schema(description = "选中座位")
  private List<SeatListDto> seatList;

}
