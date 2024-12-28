package com.vscing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/** 
 * @author vscing (vscing@foxmail.com)
 * @date 2025-12-29 00:14:22
*/
@Data
public class UserListDto {

    @Schema(description = "来源 0 -> 全部 1 -> 微信小程序 2 -> 支付宝小程序")
    private Integer source;

    @Schema(description = "用户名称")
    private String username;
}
