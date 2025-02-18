package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetPathCheckResultByIDResponsedatadetailItem {
    @SerializedName("errno")
    private long errno;

    public long getErrno() {
        return this.errno;
    }

    public void setErrno(long errno) {
        this.errno = errno;
    }

    @SerializedName("harmUrl")
    private String[] harmUrl;

    public String[] getHarmURL() {
        return this.harmUrl;
    }

    public void setHarmURL(String[] harmUrl) {
        this.harmUrl = harmUrl;
    }

    @SerializedName("msg")
    private String msg;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @SerializedName("type")
    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
