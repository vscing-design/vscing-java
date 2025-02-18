package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddTemplateRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public AddTemplateRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("id")
    private String id = "";

    public String getID() {
        return this.id;
    }

    public AddTemplateRequest setID(String id) {
        this.id = id;
        return this;
    }

    @SerializedName("keyword_id_list")
    private String keywordIdList = "";

    public String getKeywordIDList() {
        return this.keywordIdList;
    }

    public AddTemplateRequest setKeywordIDList(String keywordIdList) {
        this.keywordIdList = keywordIdList;
        return this;
    }
}
