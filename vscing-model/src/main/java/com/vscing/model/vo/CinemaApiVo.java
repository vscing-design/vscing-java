package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * CinemaApiVo
 *
 * @author vscing
 * @date 2025/1/16 23:20
 */
@Data
public class CinemaApiVo {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "影院名称")
  private String name;

  @Schema(description = "省份名称")
  private String provinceName;

  @Schema(description = "城市名称")
  private String cityName;

  @Schema(description = "区县名称")
  private String districtName;

  @Schema(description = "详细地址")
  private String address;

  @Schema(description = "经度")
  private Double lng;

  @Schema(description = "维度")
  private Double lat;

  @Schema(description = "距离km")
  private Double distance;

  @Schema(description = "最大出售价格")
  private BigDecimal maxPrice;

  @Schema(description = "最小出售价格")
  private BigDecimal minPrice;

}
