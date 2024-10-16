package com.vscing.common.domain;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;

/** 
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:51:06
*/
@Getter
@Setter
public class BaseEntity implements Serializable {

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 创建者 */
    private String createdBy;

    /** 更新时间 */
    private LocalDateTime updatedAt;

    /** 更新者 */
    private String updatedBy;

    /** 删除时间 */

    private LocalDateTime deletedAt;

    /** 删除者 */
    private String deletedBy;

    @Override
    public String toString() {
        return "BaseEntity{" +
                "createdAt='" + createdAt + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                ", deletedBy='" + deletedBy + '\'' +
                '}';
    }
}
