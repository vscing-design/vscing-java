package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class UpdateCouponBannerRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public UpdateCouponBannerRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("appRedirectPath")
    private String appRedirectPath = "";

    public String getAppRedirectPath() {
        return this.appRedirectPath;
    }

    public UpdateCouponBannerRequest setAppRedirectPath(String appRedirectPath) {
        this.appRedirectPath = appRedirectPath;
        return this;
    }

    @SerializedName("bannerId")
    private long bannerId = 0;

    public long getBannerID() {
        return this.bannerId;
    }

    public UpdateCouponBannerRequest setBannerID(long bannerId) {
        this.bannerId = bannerId;
        return this;
    }

    @SerializedName("couponId")
    private String couponId = "";

    public String getCouponID() {
        return this.couponId;
    }

    public UpdateCouponBannerRequest setCouponID(String couponId) {
        this.couponId = couponId;
        return this;
    }

    @SerializedName("picUrl")
    private String picUrl = "";

    public String getPicURL() {
        return this.picUrl;
    }

    public UpdateCouponBannerRequest setPicURL(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    @SerializedName("title")
    private String title = "";

    public String getTitle() {
        return this.title;
    }

    public UpdateCouponBannerRequest setTitle(String title) {
        this.title = title;
        return this;
    }
}
