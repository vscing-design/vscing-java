package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class DeleteResourceRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public DeleteResourceRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("app_id")
    private long appId = 0;

    public long getAppID() {
        return this.appId;
    }

    public DeleteResourceRequest setAppID(long appId) {
        this.appId = appId;
        return this;
    }

    @SerializedName("path")
    private String path = "";

    public String getPath() {
        return this.path;
    }

    public DeleteResourceRequest setPath(String path) {
        this.path = path;
        return this;
    }
}
