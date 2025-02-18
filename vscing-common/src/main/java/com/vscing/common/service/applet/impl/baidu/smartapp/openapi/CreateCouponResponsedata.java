package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class CreateCouponResponsedata {
    @SerializedName("couponId")
    private String couponId;

    public String getCouponID() {
        return this.couponId;
    }

    public void setCouponID(String couponId) {
        this.couponId = couponId;
    }
}
