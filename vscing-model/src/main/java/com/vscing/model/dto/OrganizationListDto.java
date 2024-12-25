package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author vscing (vscing@foxmail.com)
 * @date 2024-12-23 23:03:33
*/
@Data
public class OrganizationListDto {

    @Schema(description = "名称")
    private String name;

    @Schema(description = "机构类型：1->企业")
    private Integer type;

    @Schema(description = "机构ID/机构父ID")
    private Long organizationId;

}
