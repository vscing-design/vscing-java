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

  @Schema(description = "影院名称")
  private String name;

  @Schema(description = "供应商名称")
  private String supplierName;

  @Schema(description = "影院状态 0 全部 1 营业中 2 未营业")
  private Integer status;

}
