package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:51:25
*/
@Getter
@Setter
public class User extends BaseEntity {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "来源 1 -> 微信小程序 2 -> 支付宝小程序")
    private Integer source;

    @Schema(description = "用户名称")
    private String username;

    @Schema(description = "用户密码")
    private String password;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "用户手机号")
    private String phone;

    @Schema(description = "用户星级")
    private Integer star;

    @Schema(description = "用户状态  1正常 2禁用")
    private Integer status;

    @Schema(description = "备注")
    private String notes;

}
