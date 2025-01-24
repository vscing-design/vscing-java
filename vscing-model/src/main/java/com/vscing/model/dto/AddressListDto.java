package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * AddressListDto
 *
 * @author vscing
 * @date 2024/12/23 00:59
 */
@Data
public class AddressListDto {

  @Schema(description = "名称")
  private String name;

}
