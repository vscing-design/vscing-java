package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * CinemaApiDistrictVo
 *
 * @author vscing
 * @date 2025/1/18 01:38
 */
@Data
public class CinemaApiDistrictVo {

  @Schema(description = "区县编码")
  private Long id;

  @Schema(description = "区县名称")
  private String name;

  @Schema(description = "区县经度")
  private Double lng;

  @Schema(description = "区县维度")
  private Double lat;

}
