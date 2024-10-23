package com.vscing.common.api;

import lombok.Getter;

@Getter
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "成功"),
    FAILED(500, "失败"),
    VALIDATE_FAILED(404, "参数错误"),
    UNAUTHORIZED(401, "未登录"),
    FORBIDDEN(403, "暂无权限");

    private final long code;
    private final String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

}
