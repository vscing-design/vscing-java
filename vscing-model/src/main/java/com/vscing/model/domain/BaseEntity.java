package com.vscing.model.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/** 
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:51:06
*/
@Data
public class BaseEntity implements Serializable {

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 创建者 */
    private Long createdBy;

    /** 更新时间 */
    private LocalDateTime updatedAt;

    /** 更新者 */
    private Long updatedBy;

    /** 删除时间 */

    private LocalDateTime deletedAt;

    /** 删除者 */
    private Long deletedBy;

}
