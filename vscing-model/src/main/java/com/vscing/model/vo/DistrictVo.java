package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DistrictVo
 *
 * @author vscing
 * @date 2024/12/28 00:55
 */
@Data
public class DistrictVo {

  @Schema(description = "区县编码")
  private Long id;

  @Schema(description = "城市编码")
  private Long cityId;

  @Schema(description = "海威供应商城市ID (有的区县在供应商哪里是城市)")
  private Long s1CityId;

  @Schema(description = "海威供应商区县ID")
  private Long s1RegionId;

  @Schema(description = "区县名称")
  private String name;

  @Schema(description = "区县简称")
  private String shortName;

  @Schema(description = "区县编码")
  private String pinyin;

  @Schema(description = "区县首字母")
  private String firstLetter;

  @Schema(description = "区县首字母全拼")
  private String fullLetter;

  @Schema(description = "区县经度")
  private Double lng;

  @Schema(description = "区县维度")
  private Double lat;

}
