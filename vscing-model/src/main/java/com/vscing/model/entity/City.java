package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * City
 *
 * @author vscing
 * @date 2024/12/23 00:40
 */
@Getter
@Setter
public class City extends BaseEntity {

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
  private String lng;

  @Schema(description = "城市维度")
  private String lat;

}