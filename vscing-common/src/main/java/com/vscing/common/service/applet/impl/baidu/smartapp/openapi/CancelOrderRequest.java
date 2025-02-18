package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class CancelOrderRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public CancelOrderRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("orderId")
    private long orderId = 0;

    public long getOrderID() {
        return this.orderId;
    }

    public CancelOrderRequest setOrderID(long orderId) {
        this.orderId = orderId;
        return this;
    }

    @SerializedName("pmAppKey")
    private String pmAppKey = "";

    public String getPmAppKey() {
        return this.pmAppKey;
    }

    public CancelOrderRequest setPmAppKey(String pmAppKey) {
        this.pmAppKey = pmAppKey;
        return this;
    }
}
