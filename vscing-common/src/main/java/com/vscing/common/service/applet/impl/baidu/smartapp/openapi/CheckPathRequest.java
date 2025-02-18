package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class CheckPathRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public CheckPathRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("path")
    private String path = "";

    public String getPath() {
        return this.path;
    }

    public CheckPathRequest setPath(String path) {
        this.path = path;
        return this;
    }

    @SerializedName("type")
    private String[] type;

    public String[] getType() {
        return this.type;
    }

    public CheckPathRequest setType(String[] type) {
        this.type = type;
        return this;
    }
}
