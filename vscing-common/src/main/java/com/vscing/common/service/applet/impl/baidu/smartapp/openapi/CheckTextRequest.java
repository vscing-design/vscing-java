package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class CheckTextRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public CheckTextRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("content")
    private String content = "";

    public String getContent() {
        return this.content;
    }

    public CheckTextRequest setContent(String content) {
        this.content = content;
        return this;
    }

    @SerializedName("type")
    private String[] type;

    public String[] getType() {
        return this.type;
    }

    public CheckTextRequest setType(String[] type) {
        this.type = type;
        return this;
    }
}
