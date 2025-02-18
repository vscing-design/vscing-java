package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class SubmitSitemapRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public SubmitSitemapRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("app_id")
    private long appId = 0;

    public long getAppID() {
        return this.appId;
    }

    public SubmitSitemapRequest setAppID(long appId) {
        this.appId = appId;
        return this;
    }

    @SerializedName("desc")
    private String desc = "";

    public String getDesc() {
        return this.desc;
    }

    public SubmitSitemapRequest setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    @SerializedName("frequency")
    private String frequency = "";

    public String getFrequency() {
        return this.frequency;
    }

    public SubmitSitemapRequest setFrequency(String frequency) {
        this.frequency = frequency;
        return this;
    }

    @SerializedName("type")
    private String type = "";

    public String getType() {
        return this.type;
    }

    public SubmitSitemapRequest setType(String type) {
        this.type = type;
        return this;
    }

    @SerializedName("url")
    private String url = "";

    public String getURL() {
        return this.url;
    }

    public SubmitSitemapRequest setURL(String url) {
        this.url = url;
        return this;
    }
}
