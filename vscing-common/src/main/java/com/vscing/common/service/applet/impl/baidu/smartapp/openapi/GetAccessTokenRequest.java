package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetAccessTokenRequest {
    @SerializedName("grant_type")
    private String grantType = "";

    public String getGrantType() {
        return this.grantType;
    }

    public GetAccessTokenRequest setGrantType(String grantType) {
        this.grantType = grantType;
        return this;
    }

    @SerializedName("client_id")
    private String clientId = "";

    public String getClientID() {
        return this.clientId;
    }

    public GetAccessTokenRequest setClientID(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @SerializedName("client_secret")
    private String clientSecret = "";

    public String getClientSecret() {
        return this.clientSecret;
    }

    public GetAccessTokenRequest setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    @SerializedName("scope")
    private String scope = "";

    public String getScope() {
        return this.scope;
    }

    public GetAccessTokenRequest setScope(String scope) {
        this.scope = scope;
        return this;
    }
}
