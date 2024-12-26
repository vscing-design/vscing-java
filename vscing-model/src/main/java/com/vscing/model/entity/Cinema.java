package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Cinema
 *
 * @author vscing
 * @date 2024/12/26 00:37
 */
@Getter
@Setter
public class Cinema extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "三方ID")
  private Long cinemaId;

  @Schema(description = "供应商ID")
  private Long supplierId;

  @Schema(description = "供应商名称")
  private String supplierName;

  @Schema(description = "影院名称")
  private String name;

  @Schema(description = "省份ID")
  private Long provinceId;

  @Schema(description = "省份名称")
  private String provinceName;

  @Schema(description = "城市ID")
  private Long cityId;

  @Schema(description = "城市名称")
  private String cityName;

  @Schema(description = "区县ID")
  private Long districtId;

  @Schema(description = "区县名称")
  private String districtName;

  @Schema(description = "详细地址")
  private String address;

  @Schema(description = "经度")
  private String lng;

  @Schema(description = "维度")
  private String lat;

  @Schema(description = "联系方式")
  private String phone;

  @Schema(description = "状态 1 营业中 2 未营业")
  private Integer status;

}
