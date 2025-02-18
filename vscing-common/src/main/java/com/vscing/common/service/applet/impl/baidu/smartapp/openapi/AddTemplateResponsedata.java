package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddTemplateResponsedata {
    @SerializedName("template_id")
    private String templateId;

    public String getTemplateID() {
        return this.templateId;
    }

    public void setTemplateID(String templateId) {
        this.templateId = templateId;
    }
}
