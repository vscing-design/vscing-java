package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class BatchUploadCouponCodeRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public BatchUploadCouponCodeRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("couponId")
    private String couponId = "";

    public String getCouponID() {
        return this.couponId;
    }

    public BatchUploadCouponCodeRequest setCouponID(String couponId) {
        this.couponId = couponId;
        return this;
    }

    @SerializedName("couponCodes")
    private String couponCodes = "";

    public String getCouponCodes() {
        return this.couponCodes;
    }

    public BatchUploadCouponCodeRequest setCouponCodes(String couponCodes) {
        this.couponCodes = couponCodes;
        return this;
    }
}
