package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddCouponBannerRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public AddCouponBannerRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("couponId")
    private String couponId = "";

    public String getCouponID() {
        return this.couponId;
    }

    public AddCouponBannerRequest setCouponID(String couponId) {
        this.couponId = couponId;
        return this;
    }

    @SerializedName("picUrl")
    private String picUrl = "";

    public String getPicURL() {
        return this.picUrl;
    }

    public AddCouponBannerRequest setPicURL(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    @SerializedName("title")
    private String title = "";

    public String getTitle() {
        return this.title;
    }

    public AddCouponBannerRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    @SerializedName("appRedirectPath")
    private String appRedirectPath = "";

    public String getAppRedirectPath() {
        return this.appRedirectPath;
    }

    public AddCouponBannerRequest setAppRedirectPath(String appRedirectPath) {
        this.appRedirectPath = appRedirectPath;
        return this;
    }
}
