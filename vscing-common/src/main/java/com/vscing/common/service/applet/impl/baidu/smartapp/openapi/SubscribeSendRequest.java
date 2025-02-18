package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class SubscribeSendRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public SubscribeSendRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("template_id")
    private String templateId = "";

    public String getTemplateID() {
        return this.templateId;
    }

    public SubscribeSendRequest setTemplateID(String templateId) {
        this.templateId = templateId;
        return this;
    }

    @SerializedName("touser_openId")
    private String touserOpenId = "";

    public String getTouserOpenID() {
        return this.touserOpenId;
    }

    public SubscribeSendRequest setTouserOpenID(String touserOpenId) {
        this.touserOpenId = touserOpenId;
        return this;
    }

    @SerializedName("subscribe_id")
    private String subscribeId = "";

    public String getSubscribeID() {
        return this.subscribeId;
    }

    public SubscribeSendRequest setSubscribeID(String subscribeId) {
        this.subscribeId = subscribeId;
        return this;
    }

    @SerializedName("data")
    private String data = "";

    public String getData() {
        return this.data;
    }

    public SubscribeSendRequest setData(String data) {
        this.data = data;
        return this;
    }

    @SerializedName("page")
    private String page = "";

    public String getPage() {
        return this.page;
    }

    public SubscribeSendRequest setPage(String page) {
        this.page = page;
        return this;
    }
}
