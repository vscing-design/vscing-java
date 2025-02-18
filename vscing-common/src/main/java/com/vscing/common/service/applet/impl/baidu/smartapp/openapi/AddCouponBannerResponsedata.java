package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddCouponBannerResponsedata {
    @SerializedName("bannerId")
    private long bannerId;

    public long getBannerID() {
        return this.bannerId;
    }

    public void setBannerID(long bannerId) {
        this.bannerId = bannerId;
    }
}
