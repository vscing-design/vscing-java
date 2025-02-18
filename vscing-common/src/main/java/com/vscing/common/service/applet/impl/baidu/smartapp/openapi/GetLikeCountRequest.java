package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetLikeCountRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public GetLikeCountRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("host_name")
    private String hostName = "";

    public String getHostName() {
        return this.hostName;
    }

    public GetLikeCountRequest setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    @SerializedName("snid")
    private String snid = "";

    public String getSnid() {
        return this.snid;
    }

    public GetLikeCountRequest setSnid(String snid) {
        this.snid = snid;
        return this;
    }

    @SerializedName("snid_type")
    private String snidType = "";

    public String getSnidType() {
        return this.snidType;
    }

    public GetLikeCountRequest setSnidType(String snidType) {
        this.snidType = snidType;
        return this;
    }
}
