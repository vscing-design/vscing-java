package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetTemplateLibraryByIDResponsedatakeywordListItem {
    @SerializedName("example")
    private String example;

    public String getExample() {
        return this.example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @SerializedName("keyword_id")
    private long keywordId;

    public long getKeywordID() {
        return this.keywordId;
    }

    public void setKeywordID(long keywordId) {
        this.keywordId = keywordId;
    }

    @SerializedName("name")
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
