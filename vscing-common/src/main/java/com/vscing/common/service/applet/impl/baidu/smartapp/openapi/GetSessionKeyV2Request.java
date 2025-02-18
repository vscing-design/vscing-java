package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetSessionKeyV2Request {
    @SerializedName("code")
    private String code = "";

    public String getCode() {
        return this.code;
    }

    public GetSessionKeyV2Request setCode(String code) {
        this.code = code;
        return this;
    }

    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public GetSessionKeyV2Request setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("client_id")
    private String clientId = "";

    public String getClientID() {
        return this.clientId;
    }

    public GetSessionKeyV2Request setClientID(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @SerializedName("sk")
    private String sk = "";

    public String getSk() {
        return this.sk;
    }

    public GetSessionKeyV2Request setSk(String sk) {
        this.sk = sk;
        return this;
    }
}
