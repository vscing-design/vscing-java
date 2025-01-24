package com.vscing.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * SeatPriceRequest
 *
 * @author vscing
 * @date 2025/1/1 21:38
 */
@Data
public class SeatPriceRequest {

  @NotNull(message = "场次ID不能为空")
  @Schema(description = "场次id")
  private Long showId;

  @NotNull(message = "选中座位数量不能为空")
  @Schema(description = "选中座位数量")
  private Integer seatNum;

  @NotNull(message = "区域ID列表不能为空")
  @Schema(description = "区域ID，不支持分区的null值传空字符串、其他值传字符串，长度要和座位数量一致")
  private List<String> areaIds;

}
