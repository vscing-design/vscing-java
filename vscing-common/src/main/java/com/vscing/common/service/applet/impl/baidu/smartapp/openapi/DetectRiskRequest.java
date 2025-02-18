package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class DetectRiskRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public DetectRiskRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("appkey")
    private String appkey = "";

    public String getAppkey() {
        return this.appkey;
    }

    public DetectRiskRequest setAppkey(String appkey) {
        this.appkey = appkey;
        return this;
    }

    @SerializedName("xtoken")
    private String xtoken = "";

    public String getXtoken() {
        return this.xtoken;
    }

    public DetectRiskRequest setXtoken(String xtoken) {
        this.xtoken = xtoken;
        return this;
    }

    @SerializedName("type")
    private String type = "";

    public String getType() {
        return this.type;
    }

    public DetectRiskRequest setType(String type) {
        this.type = type;
        return this;
    }

    @SerializedName("clientip")
    private String clientip = "";

    public String getClientip() {
        return this.clientip;
    }

    public DetectRiskRequest setClientip(String clientip) {
        this.clientip = clientip;
        return this;
    }

    @SerializedName("ts")
    private long ts = 0;

    public long getTs() {
        return this.ts;
    }

    public DetectRiskRequest setTs(long ts) {
        this.ts = ts;
        return this;
    }

    @SerializedName("ev")
    private String ev = "";

    public String getEv() {
        return this.ev;
    }

    public DetectRiskRequest setEv(String ev) {
        this.ev = ev;
        return this;
    }

    @SerializedName("useragent")
    private String useragent = "";

    public String getUseragent() {
        return this.useragent;
    }

    public DetectRiskRequest setUseragent(String useragent) {
        this.useragent = useragent;
        return this;
    }

    @SerializedName("phone")
    private String phone = "";

    public String getPhone() {
        return this.phone;
    }

    public DetectRiskRequest setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
