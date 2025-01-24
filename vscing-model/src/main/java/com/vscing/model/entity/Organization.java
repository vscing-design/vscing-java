package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import com.vscing.model.vo.OrganizationTreeVo;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Organization
 *
 * @author vscing
 * @date 2024/12/22 00:46
 */
@Getter
@Setter
@AutoMappers({
    @AutoMapper(target = OrganizationTreeVo.class)
})
public class Organization extends BaseEntity {

    @Schema(description = "主键Id")
    private Long id;

    @Schema(description = "父级Id")
    private Long parentId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "机构类型：1->企业")
    private Integer type;

    @Schema(description = "排序")
    private Integer sortOrder;

    @Schema(description = "备注")
    private String notes;

}
