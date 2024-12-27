package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * City
 *
 * @author vscing
 * @date 2024/12/23 00:40
 */
@Data
public class CityVo {

  @Schema(description = "城市编码")
  private Long id;

  @Schema(description = "省份编码")
  private Long provinceId;

  @Schema(description = "海威供应商城市ID")
  private Long s1CityId;

  @Schema(description = "城市名称")
  private String name;

  @Schema(description = "城市简称")
  private String shortName;

  @Schema(description = "城市编码")
  private String pinyin;

  @Schema(description = "城市首字母")
  private String firstLetter;

  @Schema(description = "城市首字母全拼")
  private String fullLetter;

  @Schema(description = "城市经度")
  private Double lng;

  @Schema(description = "城市维度")
  private Double lat;

  @Schema(description = "区县列表")
  private List<DistrictVo> children;

}
