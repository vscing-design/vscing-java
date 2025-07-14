package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class QueryCinemaShow {

  @Schema(description = "影院id")
  private Long cinemaId;

  @Schema(description = "影院名称")
  private String cinemaName;

  @Schema(description = "影院地址")
  private String cinemaAddress;

  @Schema(description = "经度")
  private Double longitude;

  @Schema(description = "纬度")
  private Double latitude;

  @Schema(description = "场次总数")
  private long total;

  @Schema(description = "场次列表")
  private List<QueryShow> showList;

}
