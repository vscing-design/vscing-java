package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * SeatMapVo
 *
 * @author vscing
 * @date 2025/1/1 21:26
 */
@Data
public class SeatMapVo {

  @Schema(description = "每次下单最多可选座位个数")
  private Integer restrictions;

  @Schema(description = "座位图")
  private List<SeatVo> seats;

}
