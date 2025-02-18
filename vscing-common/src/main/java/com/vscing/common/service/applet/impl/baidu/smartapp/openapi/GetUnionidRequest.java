package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetUnionidRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public GetUnionidRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("openid")
    private String openid = "";

    public String getOpenid() {
        return this.openid;
    }

    public GetUnionidRequest setOpenid(String openid) {
        this.openid = openid;
        return this;
    }
}
