package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * ShowTreeVo
 *
 * @author vscing
 * @date 2025/1/1 14:10
 */
@Data
public class MovieTreeVo {

  @Schema(description = "供应商ID")
  private Long supplierId;

  @Schema(description = "影院ID")
  private Long cinemaId;

  @Schema(description = "影片ID")
  private Long movieId;

  @Schema(description = "影片名称")
  private String movieName;

  @Schema(description = "场次列表")
  private List<ShowTreeVo> showList;
}
