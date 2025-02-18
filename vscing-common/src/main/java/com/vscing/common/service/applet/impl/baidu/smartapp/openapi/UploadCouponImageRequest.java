package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

public class UploadCouponImageRequest {

    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public UploadCouponImageRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }
}
