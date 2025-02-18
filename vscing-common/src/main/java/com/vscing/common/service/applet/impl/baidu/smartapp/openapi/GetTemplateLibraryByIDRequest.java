package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetTemplateLibraryByIDRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public GetTemplateLibraryByIDRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("id")
    private String id = "";

    public String getID() {
        return this.id;
    }

    public GetTemplateLibraryByIDRequest setID(String id) {
        this.id = id;
        return this;
    }
}
