package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class DeleteCouponBannerRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public DeleteCouponBannerRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("couponId")
    private String couponId = "";

    public String getCouponID() {
        return this.couponId;
    }

    public DeleteCouponBannerRequest setCouponID(String couponId) {
        this.couponId = couponId;
        return this;
    }

    @SerializedName("bannerId")
    private long bannerId = 0;

    public long getBannerID() {
        return this.bannerId;
    }

    public DeleteCouponBannerRequest setBannerID(long bannerId) {
        this.bannerId = bannerId;
        return this;
    }
}
