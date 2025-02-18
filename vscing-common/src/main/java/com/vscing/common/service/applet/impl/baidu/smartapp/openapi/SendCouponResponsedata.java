package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class SendCouponResponsedata {
    @SerializedName("couponTakeId")
    private long couponTakeId;

    public long getCouponTakeID() {
        return this.couponTakeId;
    }

    public void setCouponTakeID(long couponTakeId) {
        this.couponTakeId = couponTakeId;
    }
}
