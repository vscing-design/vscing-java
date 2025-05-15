package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuerySeatDto extends QueryDto {

  @NotNull(message = "场次id未传入")
  @Schema(description = "场次id")
  private Long showId;

  @NotNull(message = "是否支持分区未传入")
  @Schema(description = "是否支持分区")
  private Integer addFlag;

}
