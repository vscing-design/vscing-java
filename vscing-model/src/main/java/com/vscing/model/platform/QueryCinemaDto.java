package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryCinemaDto extends QueryDto {

  @NotNull(message = "城市id未传入")
  @Schema(description = "城市id")
  private Long cityId;

  @NotNull(message = "区县id未传入")
  @Schema(description = "区县id")
  private Long districtId;

}
