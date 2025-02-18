package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetQrCodeResponse {
    @SerializedName("data")
    private GetQrCodeResponsedata data;

    public GetQrCodeResponsedata getData() {
        return this.data;
    }

    public void setData(GetQrCodeResponsedata data) {
        this.data = data;
    }

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

    @SerializedName("request_id")
    private String requestId;

    public String getRequestID() {
        return this.requestId;
    }

    public void setRequestID(String requestId) {
        this.requestId = requestId;
    }

    @SerializedName("timestamp")
    private long timestamp;

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
