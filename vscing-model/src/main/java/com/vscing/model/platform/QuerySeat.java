package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class QuerySeat {

  @Schema(description = "座位图列表")
  private List<QuerySeatList> seatList;

  @Schema(description = "每次下单限制选座数量")
  private int restrictions;

}
