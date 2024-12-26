package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * CinemaListDto
 *
 * @author vscing
 * @date 2024/12/26 22:08
 */
@Data
public class CinemaListDto {

  @Schema(description = "名称")
  private String name;

}
