package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class CreateCouponRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public CreateCouponRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("callbackUrl")
    private String callbackUrl = "";

    public String getCallbackURL() {
        return this.callbackUrl;
    }

    public CreateCouponRequest setCallbackURL(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    @SerializedName("couponType")
    private String couponType = "";

    public String getCouponType() {
        return this.couponType;
    }

    public CreateCouponRequest setCouponType(String couponType) {
        this.couponType = couponType;
        return this;
    }

    @SerializedName("leastCost")
    private long leastCost = 0;

    public long getLeastCost() {
        return this.leastCost;
    }

    public CreateCouponRequest setLeastCost(long leastCost) {
        this.leastCost = leastCost;
        return this;
    }

    @SerializedName("reduceCost")
    private long reduceCost = 0;

    public long getReduceCost() {
        return this.reduceCost;
    }

    public CreateCouponRequest setReduceCost(long reduceCost) {
        this.reduceCost = reduceCost;
        return this;
    }

    @SerializedName("discount")
    private String discount = "";

    public String getDiscount() {
        return this.discount;
    }

    public CreateCouponRequest setDiscount(String discount) {
        this.discount = discount;
        return this;
    }

    @SerializedName("baseInfo")
    private CreateCouponRequestbaseInfo baseInfo;

    public CreateCouponRequestbaseInfo getBaseInfo() {
        return this.baseInfo;
    }

    public CreateCouponRequest setBaseInfo(CreateCouponRequestbaseInfo baseInfo) {
        this.baseInfo = baseInfo;
        return this;
    }

    @SerializedName("description")
    private String description = "";

    public String getDescription() {
        return this.description;
    }

    public CreateCouponRequest setDescription(String description) {
        this.description = description;
        return this;
    }
}
