package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class ResetComponentStorageResponse {
    @SerializedName("errmsg")
    private String errmsg;

    public String getErrmsg() {
        return this.errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @SerializedName("errno")
    private long errno;

    public long getErrno() {
        return this.errno;
    }

    public void setErrno(long errno) {
        this.errno = errno;
    }

    @SerializedName("error_code")
    private long errorCode;

    public long getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }

    @SerializedName("error_msg")
    private String errorMsg;

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @SerializedName("logid")
    private String logid;

    public String getLogid() {
        return this.logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }
}
