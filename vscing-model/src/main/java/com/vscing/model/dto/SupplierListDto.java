package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * SupplierListDto
 *
 * @author vscing
 * @date 2024/12/20 00:49
 */
@Getter
@Setter
public class SupplierListDto {

  @Schema(description = "供应商名称")
  private String name;

  @Schema(description = "供应商状态 1 洽谈中 2 合作中 3 已暂停")
  private Integer status;

}
