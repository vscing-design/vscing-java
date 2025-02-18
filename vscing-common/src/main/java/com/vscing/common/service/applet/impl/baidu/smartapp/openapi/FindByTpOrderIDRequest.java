package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class FindByTpOrderIDRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public FindByTpOrderIDRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("tpOrderId")
    private String tpOrderId = "";

    public String getTpOrderID() {
        return this.tpOrderId;
    }

    public FindByTpOrderIDRequest setTpOrderID(String tpOrderId) {
        this.tpOrderId = tpOrderId;
        return this;
    }

    @SerializedName("pmAppKey")
    private String pmAppKey = "";

    public String getPmAppKey() {
        return this.pmAppKey;
    }

    public FindByTpOrderIDRequest setPmAppKey(String pmAppKey) {
        this.pmAppKey = pmAppKey;
        return this;
    }
}
