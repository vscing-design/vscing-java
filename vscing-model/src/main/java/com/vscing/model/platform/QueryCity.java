package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class QueryCity {

  @Schema(description = "城市id")
  private Long cityId;

  @Schema(description = "城市名称")
  private String cityName;

  @Schema(description = "城市首字母")
  private String firstLetter;

  @Schema(description = "城市热度值")
  private Integer hot;

  @Schema(description = "省份id")
  private Long provinceId;

  @Schema(description = "省份名称")
  private String provinceName;

  @Schema(description = "区县列表")
  private List<QueryDistrict> districtList;

}
