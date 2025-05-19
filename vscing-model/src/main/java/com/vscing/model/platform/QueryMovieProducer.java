package com.vscing.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryMovieProducer {

  @Schema(description = "影片ID")
  private Long movieId;

  @Schema(description = "类型 1-导演 2-演员")
  private Integer type;

  @Schema(description = "导演/演员图片")
  private String avatar;

  @Schema(description = "导演/演员英文名称")
  private String enName;

  @Schema(description = "导演/演员中文名称")
  private String scName;

  @Schema(description = "演员角色名称")
  private String actName;

}
