package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetCouponRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public GetCouponRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("couponId")
    private String couponId = "";

    public String getCouponID() {
        return this.couponId;
    }

    public GetCouponRequest setCouponID(String couponId) {
        this.couponId = couponId;
        return this;
    }
}
