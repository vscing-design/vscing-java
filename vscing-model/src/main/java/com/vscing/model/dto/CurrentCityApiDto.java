package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * CurrentCityApiDto
 *
 * @author vscing
 * @date 2025/1/14 14:10
 */
@Data
public class CurrentCityApiDto {

  @Schema(description = "城市经度")
  private Double lng;

  @Schema(description = "城市维度")
  private Double lat;

}
