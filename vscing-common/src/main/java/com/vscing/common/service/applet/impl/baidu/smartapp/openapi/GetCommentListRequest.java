package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetCommentListRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public GetCommentListRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("host_name")
    private String hostName = "";

    public String getHostName() {
        return this.hostName;
    }

    public GetCommentListRequest setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    @SerializedName("snid")
    private String snid = "";

    public String getSnid() {
        return this.snid;
    }

    public GetCommentListRequest setSnid(String snid) {
        this.snid = snid;
        return this;
    }

    @SerializedName("snid_type")
    private String snidType = "";

    public String getSnidType() {
        return this.snidType;
    }

    public GetCommentListRequest setSnidType(String snidType) {
        this.snidType = snidType;
        return this;
    }

    @SerializedName("start")
    private long start = 0;

    public long getStart() {
        return this.start;
    }

    public GetCommentListRequest setStart(long start) {
        this.start = start;
        return this;
    }

    @SerializedName("num")
    private long num = 0;

    public long getNum() {
        return this.num;
    }

    public GetCommentListRequest setNum(long num) {
        this.num = num;
        return this;
    }
}
