package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetSessionKeyRequest {
    @SerializedName("code")
    private String code = "";

    public String getCode() {
        return this.code;
    }

    public GetSessionKeyRequest setCode(String code) {
        this.code = code;
        return this;
    }

    @SerializedName("client_id")
    private String clientId = "";

    public String getClientID() {
        return this.clientId;
    }

    public GetSessionKeyRequest setClientID(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @SerializedName("sk")
    private String sk = "";

    public String getSk() {
        return this.sk;
    }

    public GetSessionKeyRequest setSk(String sk) {
        this.sk = sk;
        return this;
    }
}
