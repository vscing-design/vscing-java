package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class OrganizationListDto {

    @Schema(description = "名称")
    private String name;

    @Schema(description = "机构类型：1->企业")
    private Integer type;

}
