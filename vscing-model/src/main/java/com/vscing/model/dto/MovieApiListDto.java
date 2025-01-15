package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * MovieApiListDto
 *
 * @author vscing
 * @date 2025/1/13 10:17
 */
@Data
public class MovieApiListDto {

  @Schema(description = "影片名称")
  private String name;

  @Schema(description = "城市ID")
  private Long cityId;

  @Schema(description = "区县ID")
  private Long districtId;

  @Schema(description = "上映类型，HOT为热映，WAIT为待上映")
  private String publishStatus;

}
