package com.vscing.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:52:31
*/
@Getter
@Setter
public class UserVo {

  private Long id;

    // 用户名称
    private String username;

    // 用户邮箱
    private String password;

    // 用户手机号
    private String phone;

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
}
