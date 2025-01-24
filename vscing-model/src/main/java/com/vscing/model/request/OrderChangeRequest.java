package com.vscing.model.request;

import com.vscing.model.dto.SeatListDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * OrderChangeRequest
 *
 * @author vscing
 * @date 2025/1/5 18:27
 */
@Data
public class OrderChangeRequest {
  @NotNull(message = "订单ID不能为空")
  @Schema(description = "订单ID")
  private Long id;

  @NotNull(message = "选中座位不能为空")
  @Schema(description = "选中座位")
  private List<SeatListDto> seatList;



}
