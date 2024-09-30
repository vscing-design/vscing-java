package com.vscing.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.vscing.common.domain.BaseEntity;

/**
 * 用户关系实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {

    private Long id;

    // 用户名称
    private String username;

    // 用户邮箱
    private String password;

    // 用户手机号
    private String phone;


}
