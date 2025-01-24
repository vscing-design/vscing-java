package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * MovieShowArea
 *
 * @author vscing
 * @date 2024/12/27 21:16
 */
@Getter
@Setter
public class ShowArea extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "影片场次ID")
  private Long showId;

  @Schema(description = "区域ID,可能为字母")
  private String area;

  @Schema(description = "场次价格（元）")
  private BigDecimal showPrice;

  @Schema(description = "影片结算价（元）")
  private BigDecimal userPrice;

}
