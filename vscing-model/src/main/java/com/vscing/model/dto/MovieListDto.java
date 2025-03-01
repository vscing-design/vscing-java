package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * MovieListDto
 *
 * @author vscing
 * @date 2024/12/27 21:21
 */
@Data
public class MovieListDto {

  @Schema(description = "影片名称")
  private String name;

  @Schema(description = "上映类型，HOT为热映，WAIT为待上映")
  private String publishStatus;

}
