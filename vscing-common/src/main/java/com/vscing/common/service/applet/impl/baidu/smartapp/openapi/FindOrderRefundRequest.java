package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class FindOrderRefundRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public FindOrderRefundRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("tpOrderId")
    private String tpOrderId = "";

    public String getTpOrderID() {
        return this.tpOrderId;
    }

    public FindOrderRefundRequest setTpOrderID(String tpOrderId) {
        this.tpOrderId = tpOrderId;
        return this;
    }

    @SerializedName("userId")
    private long userId = 0;

    public long getUserID() {
        return this.userId;
    }

    public FindOrderRefundRequest setUserID(long userId) {
        this.userId = userId;
        return this;
    }

    @SerializedName("pmAppKey")
    private String pmAppKey = "";

    public String getPmAppKey() {
        return this.pmAppKey;
    }

    public FindOrderRefundRequest setPmAppKey(String pmAppKey) {
        this.pmAppKey = pmAppKey;
        return this;
    }
}
