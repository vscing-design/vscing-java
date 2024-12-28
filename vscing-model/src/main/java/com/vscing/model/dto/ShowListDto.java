package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * ShowListDto
 *
 * @author vscing
 * @date 2024/12/29 00:31
 */
@Data
public class ShowListDto {

  @Schema(description = "影院名称")
  private Long cinemaName;

  @Schema(description = "供应商名称")
  private Long supplierName;

}
