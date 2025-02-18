package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetTemplateListResponsedatalistItem {
    @SerializedName("content")
    private String content;

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @SerializedName("example")
    private String example;

    public String getExample() {
        return this.example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @SerializedName("template_id")
    private String templateId;

    public String getTemplateID() {
        return this.templateId;
    }

    public void setTemplateID(String templateId) {
        this.templateId = templateId;
    }

    @SerializedName("title")
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
