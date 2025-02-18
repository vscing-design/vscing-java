package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class SendTemplateMessageRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public SendTemplateMessageRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("template_id")
    private String templateId = "";

    public String getTemplateID() {
        return this.templateId;
    }

    public SendTemplateMessageRequest setTemplateID(String templateId) {
        this.templateId = templateId;
        return this;
    }

    @SerializedName("touser_openId")
    private String touserOpenId = "";

    public String getTouserOpenID() {
        return this.touserOpenId;
    }

    public SendTemplateMessageRequest setTouserOpenID(String touserOpenId) {
        this.touserOpenId = touserOpenId;
        return this;
    }

    @SerializedName("data")
    private String data = "";

    public String getData() {
        return this.data;
    }

    public SendTemplateMessageRequest setData(String data) {
        this.data = data;
        return this;
    }

    @SerializedName("page")
    private String page = "";

    public String getPage() {
        return this.page;
    }

    public SendTemplateMessageRequest setPage(String page) {
        this.page = page;
        return this;
    }

    @SerializedName("scene_id")
    private String sceneId = "";

    public String getSceneID() {
        return this.sceneId;
    }

    public SendTemplateMessageRequest setSceneID(String sceneId) {
        this.sceneId = sceneId;
        return this;
    }

    @SerializedName("scene_type")
    private long sceneType = 0;

    public long getSceneType() {
        return this.sceneType;
    }

    public SendTemplateMessageRequest setSceneType(long sceneType) {
        this.sceneType = sceneType;
        return this;
    }
}
