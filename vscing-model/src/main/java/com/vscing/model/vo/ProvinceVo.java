package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Province
 *
 * @author vscing
 * @date 2024/12/23 00:36
 */
@Data
public class ProvinceVo {

  @Schema(description = "省份编码")
  private Long id;

  @Schema(description = "省份名称")
  private String name;

  @Schema(description = "省份简称")
  private String shortName;

  @Schema(description = "省份编码")
  private String pinyin;

  @Schema(description = "省份首字母")
  private String firstLetter;

  @Schema(description = "省份首字母全拼")
  private String fullLetter;

  @Schema(description = "省份经度")
  private String lng;

  @Schema(description = "省份维度")
  private String lat;

  @Schema(description = "城市列表")
  private List<CityVo> children;

}
