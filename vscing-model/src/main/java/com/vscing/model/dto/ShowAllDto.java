package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * ShowAllDto
 *
 * @author vscing
 * @date 2025/1/1 13:30
 */
@Data
public class ShowAllDto {

  @Schema(description = "供应商ID")
  private Long supplierId;

  @Schema(description = "影院ID")
  private Long cinemaId;

}
