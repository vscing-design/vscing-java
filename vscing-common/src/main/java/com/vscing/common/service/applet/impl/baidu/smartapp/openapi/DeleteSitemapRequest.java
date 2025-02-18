package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class DeleteSitemapRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public DeleteSitemapRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("app_id")
    private long appId = 0;

    public long getAppID() {
        return this.appId;
    }

    public DeleteSitemapRequest setAppID(long appId) {
        this.appId = appId;
        return this;
    }

    @SerializedName("url")
    private String url = "";

    public String getURL() {
        return this.url;
    }

    public DeleteSitemapRequest setURL(String url) {
        this.url = url;
        return this;
    }
}
