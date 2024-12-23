package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * RoleListDto
 *
 * @author vscing
 * @date 2024/12/22 00:46
 */
@Data
public class RoleListDto {

    @Schema(description = "角色名称")
    private String name;
  
}
