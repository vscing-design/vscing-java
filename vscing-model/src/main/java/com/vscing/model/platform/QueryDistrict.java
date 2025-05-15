package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryDistrict {

  @Schema(description = "城市id")
  private Long cityId;

  @Schema(description = "区县id")
  private Long districtId;

  @Schema(description = "区县名称")
  private String districtName;

}
