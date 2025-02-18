package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetCouponBannerRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public GetCouponBannerRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("couponId")
    private String couponId = "";

    public String getCouponID() {
        return this.couponId;
    }

    public GetCouponBannerRequest setCouponID(String couponId) {
        this.couponId = couponId;
        return this;
    }

    @SerializedName("bannerIds")
    private String bannerIds = "";

    public String getBannerIds() {
        return this.bannerIds;
    }

    public GetCouponBannerRequest setBannerIds(String bannerIds) {
        this.bannerIds = bannerIds;
        return this;
    }
}
