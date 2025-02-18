package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class ResetComponentStorageRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public ResetComponentStorageRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }
}
