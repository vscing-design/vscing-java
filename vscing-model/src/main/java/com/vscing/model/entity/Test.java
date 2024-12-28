package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Test
 *
 * @author vscing
 * @date 2024/12/28 23:57
 */
@Getter
@Setter
// 使用lombok 来重写toString和
//@Data
//@EqualsAndHashCode(callSuper = true)
//@ToString(callSuper = true)
public class Test extends BaseEntity {

  //    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", userName='" + username + '\'' +
//                ", passWord='" + password + '\'' +
//                ", phone='" + phone + '\'' +
//                super.toString() + // 调用 BaseEntity 的 toString 方法
//                '}';
//    }

}
