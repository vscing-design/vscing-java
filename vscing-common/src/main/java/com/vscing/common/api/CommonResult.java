package com.vscing.common.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult<T> {
    private int code;
    private String message;
    private T result; // 使用泛型T，可以代表任何类型

    public CommonResult() {
    }

    public CommonResult(int code, String message) {
        this.code = code;
        this.message = message;
        this.result = null;
    }

    public CommonResult(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }
}
