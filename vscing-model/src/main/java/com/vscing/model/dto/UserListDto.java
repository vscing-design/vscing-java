package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/** 
 * @author vscing (vscing@foxmail.com)
 * @date 2025-12-29 00:14:22
*/
@Data
public class UserListDto {

    @Schema(description = "用户名称")
    private String username;

    @Schema(description = "来源 0 全部 1 微信小程序 2 支付宝小程序 3 百度小程序 21 淘宝 22 咸鱼 23 拼多多 24 微信")
    private Integer source;

    @Schema(description = "推广用户手机号")
    private String phone;

    @Schema(description = "推广用户状态 0 全部 1 合作中 2 已暂停 3 洽谈中")
    private Integer earnStatus;
}
