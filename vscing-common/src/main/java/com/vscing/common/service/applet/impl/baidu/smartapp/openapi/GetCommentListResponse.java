package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetCommentListResponse {
    @SerializedName("data")
    private GetCommentListResponsedata data;

    public GetCommentListResponsedata getData() {
        return this.data;
    }

    public void setData(GetCommentListResponsedata data) {
        this.data = data;
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

    @SerializedName("msg")
    private String msg;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @SerializedName("request_id")
    private String requestId;

    public String getRequestID() {
        return this.requestId;
    }

    public void setRequestID(String requestId) {
        this.requestId = requestId;
    }
}
