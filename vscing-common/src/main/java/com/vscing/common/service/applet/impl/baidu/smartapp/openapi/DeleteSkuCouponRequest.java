package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class DeleteSkuCouponRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public DeleteSkuCouponRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("app_id")
    private long appId = 0;

    public long getAppID() {
        return this.appId;
    }

    public DeleteSkuCouponRequest setAppID(long appId) {
        this.appId = appId;
        return this;
    }

    @SerializedName("path_list")
    private String pathList = "";

    public String getPathList() {
        return this.pathList;
    }

    public DeleteSkuCouponRequest setPathList(String pathList) {
        this.pathList = pathList;
        return this;
    }
}
