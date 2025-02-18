package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetSessionKeyV2Responsedata {
    @SerializedName("open_id")
    private String openId;

    public String getOpenID() {
        return this.openId;
    }

    public void setOpenID(String openId) {
        this.openId = openId;
    }

    @SerializedName("session_key")
    private String sessionKey;

    public String getSessionKey() {
        return this.sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
