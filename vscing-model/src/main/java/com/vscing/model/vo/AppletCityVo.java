package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * AppletCity
 *
 * @author vscing
 * @date 2025/1/11 17:55
 */
@Data
public class AppletCityVo {

  @Schema(description = "城市编码")
  private Long cityId;

  @Schema(description = "地级市编码")
  private Long districtId;

  @Schema(description = "城市名称")
  private String cityName;

  @Schema(description = "名称首字母")
  private String firstLetter;

  @Schema(description = "城市热度值")
  private Integer hot;

  @Schema(description = "城市经度")
  private Double lng;

  @Schema(description = "城市维度")
  private Double lat;

}
