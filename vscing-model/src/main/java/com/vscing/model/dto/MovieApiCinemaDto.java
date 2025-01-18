package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * MovieApiCinemaDto
 *
 * @author vscing
 * @date 2025/1/18 19:43
 */
@Data
public class MovieApiCinemaDto {

  @Schema(description = "影片ID")
  private Long movieId;

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

  @Schema(description = "排序规则 1 距离最近 2 价格最低")
  private Integer orderBy;

}