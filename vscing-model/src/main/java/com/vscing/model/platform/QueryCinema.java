package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryCinema {

  @Schema(description = "影院id")
  private Long cinemaId;

  @Schema(description = "影院名称")
  private String cinemaName;

  @Schema(description = "影院地址")
  private String cinemaAddress;

  @Schema(description = "影院电话")
  private String cinemaPhone;

  @Schema(description = "省份id")
  private Long provinceId;

  @Schema(description = "城市id")
  private Long cityId;

  @Schema(description = "区县id")
  private Long districtId;

  @Schema(description = "经度")
  private Double longitude;

  @Schema(description = "纬度")
  private Double latitude;

}
