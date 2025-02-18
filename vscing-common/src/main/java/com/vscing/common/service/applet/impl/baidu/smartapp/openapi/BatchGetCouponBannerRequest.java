package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class BatchGetCouponBannerRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public BatchGetCouponBannerRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("couponId")
    private String couponId = "";

    public String getCouponID() {
        return this.couponId;
    }

    public BatchGetCouponBannerRequest setCouponID(String couponId) {
        this.couponId = couponId;
        return this;
    }
}
