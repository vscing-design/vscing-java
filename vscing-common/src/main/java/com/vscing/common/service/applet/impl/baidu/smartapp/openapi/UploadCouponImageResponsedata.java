package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class UploadCouponImageResponsedata {
    @SerializedName("url")
    private String url;

    public String getURL() {
        return this.url;
    }

    public void setURL(String url) {
        this.url = url;
    }
}
