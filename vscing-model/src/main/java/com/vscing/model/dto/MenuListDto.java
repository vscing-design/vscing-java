package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * MenuListDto
 *
 * @author vscing
 * @date 2024/12/22 00:46
 */
@Data
public class MenuListDto {

    @Schema(description = "菜单名称")
    private String name;
  
}
