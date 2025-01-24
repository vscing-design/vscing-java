package com.vscing.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/** 
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:51:06
*/
@Data
public class BaseEntity implements Serializable {

    /**
     * 序列化版本 UID
    */
    private static final long serialVersionUID = 1L;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "创建者ID")
    private Long createdBy;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    @Schema(description = "更新者ID")
    private Long updatedBy;

    @Schema(description = "删除时间")
    private LocalDateTime deletedAt;

    @Schema(description = "删除者ID")
    private Long deletedBy;

}
