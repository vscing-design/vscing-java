package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class CapitaBillRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public CapitaBillRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("billTime")
    private String billTime = "";

    public String getBillTime() {
        return this.billTime;
    }

    public CapitaBillRequest setBillTime(String billTime) {
        this.billTime = billTime;
        return this;
    }

    @SerializedName("pmAppKey")
    private String pmAppKey = "";

    public String getPmAppKey() {
        return this.pmAppKey;
    }

    public CapitaBillRequest setPmAppKey(String pmAppKey) {
        this.pmAppKey = pmAppKey;
        return this;
    }
}
