package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetCommentCountRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public GetCommentCountRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("snid")
    private String snid = "";

    public String getSnid() {
        return this.snid;
    }

    public GetCommentCountRequest setSnid(String snid) {
        this.snid = snid;
        return this;
    }

    @SerializedName("snid_type")
    private String snidType = "";

    public String getSnidType() {
        return this.snidType;
    }

    public GetCommentCountRequest setSnidType(String snidType) {
        this.snidType = snidType;
        return this;
    }
}
