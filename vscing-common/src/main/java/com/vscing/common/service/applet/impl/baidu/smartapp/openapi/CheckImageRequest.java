package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

public class CheckImageRequest {

    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public CheckImageRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    private String type = "";

    public String getType() {
        return this.type;
    }

    public CheckImageRequest setType(String type) {
        this.type = type;
        return this;
    }
}
