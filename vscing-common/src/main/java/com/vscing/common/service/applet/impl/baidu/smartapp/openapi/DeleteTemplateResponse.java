package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class DeleteTemplateResponse {
    @SerializedName("errno")
    private long errno;

    public long getErrno() {
        return this.errno;
    }

    public void setErrno(long errno) {
        this.errno = errno;
    }

    @SerializedName("msg")
    private String msg;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
