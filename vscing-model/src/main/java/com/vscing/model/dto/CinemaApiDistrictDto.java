package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * CinemaApiDistrictDto
 *
 * @author vscing
 * @date 2025/1/18 01:39
 */
@Data
public class CinemaApiDistrictDto {

  @NotNull(message = "城市ID不能为空")
  @Schema(description = "城市ID")
  private Long cityId;

  @Schema(description = "区县ID")
  private Long districtId;

}
