package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class ReturnCouponRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public ReturnCouponRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("couponId")
    private String couponId = "";

    public String getCouponID() {
        return this.couponId;
    }

    public ReturnCouponRequest setCouponID(String couponId) {
        this.couponId = couponId;
        return this;
    }

    @SerializedName("openId")
    private String openId = "";

    public String getOpenID() {
        return this.openId;
    }

    public ReturnCouponRequest setOpenID(String openId) {
        this.openId = openId;
        return this;
    }

    @SerializedName("couponTakeId")
    private long couponTakeId = 0;

    public long getCouponTakeID() {
        return this.couponTakeId;
    }

    public ReturnCouponRequest setCouponTakeID(long couponTakeId) {
        this.couponTakeId = couponTakeId;
        return this;
    }
}
