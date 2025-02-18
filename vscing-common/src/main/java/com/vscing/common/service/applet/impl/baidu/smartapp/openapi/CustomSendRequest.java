package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class CustomSendRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public CustomSendRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("user_type")
    private long userType = 0;

    public long getUserType() {
        return this.userType;
    }

    public CustomSendRequest setUserType(long userType) {
        this.userType = userType;
        return this;
    }

    @SerializedName("open_id")
    private String openId = "";

    public String getOpenID() {
        return this.openId;
    }

    public CustomSendRequest setOpenID(String openId) {
        this.openId = openId;
        return this;
    }

    @SerializedName("msg_type")
    private String msgType = "";

    public String getMsgType() {
        return this.msgType;
    }

    public CustomSendRequest setMsgType(String msgType) {
        this.msgType = msgType;
        return this;
    }

    @SerializedName("content")
    private String content = "";

    public String getContent() {
        return this.content;
    }

    public CustomSendRequest setContent(String content) {
        this.content = content;
        return this;
    }

    @SerializedName("pic_url")
    private String picUrl = "";

    public String getPicURL() {
        return this.picUrl;
    }

    public CustomSendRequest setPicURL(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }
}
