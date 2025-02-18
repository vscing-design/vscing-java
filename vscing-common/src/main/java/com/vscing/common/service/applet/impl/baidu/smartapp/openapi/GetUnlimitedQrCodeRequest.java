package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetUnlimitedQrCodeRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public GetUnlimitedQrCodeRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("path")
    private String path = "";

    public String getPath() {
        return this.path;
    }

    public GetUnlimitedQrCodeRequest setPath(String path) {
        this.path = path;
        return this;
    }

    @SerializedName("width")
    private long width = 0;

    public long getWidth() {
        return this.width;
    }

    public GetUnlimitedQrCodeRequest setWidth(long width) {
        this.width = width;
        return this;
    }

    @SerializedName("mf")
    private long mf = 0;

    public long getMf() {
        return this.mf;
    }

    public GetUnlimitedQrCodeRequest setMf(long mf) {
        this.mf = mf;
        return this;
    }

    @SerializedName("img_flag")
    private long imgFlag = 0;
    public long getImgFlag() {
        return this.imgFlag;
    }

    public GetUnlimitedQrCodeRequest setImageFlag(long imgFlag) {
        this.imgFlag = imgFlag;
        return this;
    }
}
