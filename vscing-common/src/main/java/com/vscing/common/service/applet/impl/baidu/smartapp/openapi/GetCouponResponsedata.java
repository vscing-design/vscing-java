package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetCouponResponsedata {
    @SerializedName("baseInfo")
    private GetCouponResponsedatabaseInfo baseInfo;

    public GetCouponResponsedatabaseInfo getBaseInfo() {
        return this.baseInfo;
    }

    public void setBaseInfo(GetCouponResponsedatabaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    @SerializedName("callbackUrl")
    private String callbackUrl;

    public String getCallbackURL() {
        return this.callbackUrl;
    }

    public void setCallbackURL(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    @SerializedName("couponId")
    private String couponId;

    public String getCouponID() {
        return this.couponId;
    }

    public void setCouponID(String couponId) {
        this.couponId = couponId;
    }

    @SerializedName("couponType")
    private String couponType;

    public String getCouponType() {
        return this.couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    @SerializedName("description")
    private String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("discount")
    private long discount;

    public long getDiscount() {
        return this.discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }
}
