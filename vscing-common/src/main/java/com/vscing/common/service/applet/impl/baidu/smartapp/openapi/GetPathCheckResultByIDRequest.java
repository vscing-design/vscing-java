package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetPathCheckResultByIDRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public GetPathCheckResultByIDRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("retrieveId")
    private String retrieveId = "";

    public String getRetrieveID() {
        return this.retrieveId;
    }

    public GetPathCheckResultByIDRequest setRetrieveID(String retrieveId) {
        this.retrieveId = retrieveId;
        return this;
    }
}
