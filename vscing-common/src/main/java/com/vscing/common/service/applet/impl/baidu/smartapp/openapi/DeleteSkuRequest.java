package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class DeleteSkuRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public DeleteSkuRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("app_id")
    private long appId = 0;

    public long getAppID() {
        return this.appId;
    }

    public DeleteSkuRequest setAppID(long appId) {
        this.appId = appId;
        return this;
    }

    @SerializedName("path_list")
    private String pathList = "";

    public String getPathList() {
        return this.pathList;
    }

    public DeleteSkuRequest setPathList(String pathList) {
        this.pathList = pathList;
        return this;
    }
}
