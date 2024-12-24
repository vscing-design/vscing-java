package com.vscing.common.api;

import lombok.Getter;

/**
 * ResultCode
 * @author vscing (vscing@foxmail.com)
 * @date 2024-12-25 00:20:10
*/
@Getter
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "成功"),
    FAILED(500, "失败"),
    VALIDATE_FAILED(400, "参数错误"),
    UNAUTHORIZED(401, "未登录"),
    FORBIDDEN(403, "暂无权限");

    private final long code;
    private final String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

}
