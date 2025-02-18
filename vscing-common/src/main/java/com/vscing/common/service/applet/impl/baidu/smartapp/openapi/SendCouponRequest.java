package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class SendCouponRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public SendCouponRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("couponId")
    private String couponId = "";

    public String getCouponID() {
        return this.couponId;
    }

    public SendCouponRequest setCouponID(String couponId) {
        this.couponId = couponId;
        return this;
    }

    @SerializedName("openId")
    private String openId = "";

    public String getOpenID() {
        return this.openId;
    }

    public SendCouponRequest setOpenID(String openId) {
        this.openId = openId;
        return this;
    }

    @SerializedName("uniqueSendingIdentity")
    private String uniqueSendingIdentity = "";

    public String getUniqueSendingIdentity() {
        return this.uniqueSendingIdentity;
    }

    public SendCouponRequest setUniqueSendingIdentity(String uniqueSendingIdentity) {
        this.uniqueSendingIdentity = uniqueSendingIdentity;
        return this;
    }
}
