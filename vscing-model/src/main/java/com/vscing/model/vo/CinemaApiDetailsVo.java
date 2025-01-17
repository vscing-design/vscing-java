package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * CinemaApiDetailsVo
 *
 * @author vscing
 * @date 2025/1/18 01:39
 */
@Data
public class CinemaApiDetailsVo {

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

  @Schema(description = "距离KM")
  private Double distance;

  @Schema(description = "联系方式")
  private String phone;

  @Schema(description = "状态 1 营业中 2 未营业")
  private Integer status;

  @Schema(description = "场次列表")
  private List<CinemaApiDetailsShowVo> showList;

}
