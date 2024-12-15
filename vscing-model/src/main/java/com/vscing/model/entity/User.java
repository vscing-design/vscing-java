package com.vscing.model.entity;

import com.vscing.common.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:51:25
*/
@Getter
@Setter
// 使用lombok 来重写toString和
//@Data
//@EqualsAndHashCode(callSuper = true)
//@ToString(callSuper = true)
public class User extends BaseEntity {

    private Long id;

    // 用户名称
    private String username;

    // 用户邮箱
    private String password;

    // 用户手机号
    private String phone;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                ", passWord='" + password + '\'' +
                ", phone='" + phone + '\'' +
                super.toString() + // 调用 BaseEntity 的 toString 方法
                '}';
    }
}
