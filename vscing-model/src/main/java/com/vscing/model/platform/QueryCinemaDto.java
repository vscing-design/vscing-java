package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryCinemaDto extends QueryDto {

  @Schema(description = "当前页，默认1")
  private Integer pageNum = 1;

  @Max(value = 50, message = "单页数量最大值为50")
  @Schema(description = "单页数量，默认10，最大50")
  private Integer pageSize = 10;

  @NotNull(message = "城市id未传入")
  @Schema(description = "城市id")
  private Long cityId;

  @Schema(description = "区县id")
  private Long districtId;

}
