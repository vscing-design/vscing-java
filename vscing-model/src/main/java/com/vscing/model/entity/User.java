package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import com.vscing.model.vo.UserDetailVo;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:51:25
*/
@Getter
@Setter
@AutoMappers({
    @AutoMapper(target = UserDetailVo.class),
})
public class User extends BaseEntity {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "上级用户ID")
    private Long firstUserId;

    @Schema(description = "来源 1 微信小程序 2 支付宝小程序 3 百度小程序")
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

    @Schema(description = "待提现金额")
    private BigDecimal pendingAmount;

    @Schema(description = "已提现金额")
    private BigDecimal withdrawnAmount;

    @Schema(description = "累计佣金")
    private BigDecimal totalAmount;

    @Schema(description = "分享二维码")
    private String inviteQrcode;

    @Schema(description = "用户星级")
    private Integer star;

    @Schema(description = "用户状态  1正常 2禁用")
    private Integer status;

    @Schema(description = "推广用户状态 1 合作中 2 已暂停 3 洽谈中")
    private Integer earnStatus;

    @Schema(description = "备注")
    private String notes;

}
