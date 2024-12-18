package com.vscing.common.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult<T> {
    private long code;
    private String message;

    /**
     * 使用泛型T，可以代表任何类型
    */
    private T result;

    public CommonResult() {
    }

    public CommonResult(long code, String message) {
        this.code = code;
        this.message = message;
        this.result = null;
    }

    public CommonResult(long code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    // 成功返回结果
    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    // 成功返回结果
    public static <T> CommonResult<T> success(String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, null);
    }

    // 成功返回结果
    public static <T> CommonResult<T> success(T result) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), result);
    }

    // 成功返回结果
    public static <T> CommonResult<T> success(String message, T result) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, result);
    }

    // 失败返回结果
    public static <T> CommonResult<T> failed() {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), null);
    }

    // 失败返回结果
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
    }

    // 失败返回结果
    public static <T> CommonResult<T> failed(T result) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), result);
    }

    // 失败返回结果
    public static <T> CommonResult<T> failed(String message, T result) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, result);
    }

    // 校验返回结果
    public static <T> CommonResult<T> validateFailed() {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null);
    }

    // 校验返回结果
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    // 校验返回结果
    public static <T> CommonResult<T> validateFailed(String message, T result) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, result);
    }

    // 未登录返回结果
    public static <T> CommonResult<T> unauthorized() {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), null);
    }

    // 未登录返回结果
    public static <T> CommonResult<T> unauthorized(String message) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), message, null);
    }

    // 未登录返回结果
    public static <T> CommonResult<T> unauthorized(String message, T result) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), message, result);
    }

    // 未授权返回结果
    public static <T> CommonResult<T> forbidden() {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), null);
    }

    // 未授权返回结果
    public static <T> CommonResult<T> forbidden(String message) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), message, null);
    }

    // 未授权返回结果
    public static <T> CommonResult<T> forbidden(String message, T result) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), message, result);
    }
}


