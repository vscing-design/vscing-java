package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * CinemaApiListDto
 *
 * @author vscing
 * @date 2025/1/16 23:17
 */
@Data
public class CinemaApiListDto {

  @Schema(description = "影院名称或地址")
  private String keyword;

  @Schema(description = "城市ID")
  private Long cityId;

  @Schema(description = "区县ID")
  private Long districtId;

  @Schema(description = "经度")
  private Double lng;

  @Schema(description = "维度")
  private Double lat;

}
