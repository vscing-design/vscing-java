package com.vscing.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * ShowSeatRequest
 *
 * @author vscing
 * @date 2024/12/29 22:52
 */
@Data
public class ShowSeatRequest {

  @NotNull(message = "场次ID不能为空")
  @Schema(description = "场次id")
  private Long showId;

  @NotNull(message = "是否支持分区定价不能为空")
  @Schema(description = "是否支持分区定价；0 代表不支持 1 代表支持；不支持分区定价时，座位图中将价格不一致的座位置为不可选状态。")
  private Integer addFlag;

}
