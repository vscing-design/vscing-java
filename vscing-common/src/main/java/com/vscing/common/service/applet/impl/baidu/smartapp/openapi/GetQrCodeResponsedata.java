package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetQrCodeResponsedata {
    @SerializedName("base64_str")
    private String base64Str;

    public String getBase64Str() {
        return this.base64Str;
    }

    public void setBase64Str(String base64Str) {
        this.base64Str = base64Str;
    }

    @SerializedName("url")
    private String url;

    public String getURL() {
        return this.url;
    }

    public void setURL(String url) {
        this.url = url;
    }
}
