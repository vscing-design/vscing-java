package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class OrderBillRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public OrderBillRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("billTime")
    private String billTime = "";

    public String getBillTime() {
        return this.billTime;
    }

    public OrderBillRequest setBillTime(String billTime) {
        this.billTime = billTime;
        return this;
    }

    @SerializedName("pmAppKey")
    private String pmAppKey = "";

    public String getPmAppKey() {
        return this.pmAppKey;
    }

    public OrderBillRequest setPmAppKey(String pmAppKey) {
        this.pmAppKey = pmAppKey;
        return this;
    }
}
