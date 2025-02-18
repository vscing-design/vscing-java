package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class DeleteTemplateRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public DeleteTemplateRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("template_id")
    private String templateId = "";

    public String getTemplateID() {
        return this.templateId;
    }

    public DeleteTemplateRequest setTemplateID(String templateId) {
        this.templateId = templateId;
        return this;
    }
}
