package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class SendTemplateMessageResponsedata {
    @SerializedName("msg_key")
    private long msgKey;

    public long getMsgKey() {
        return this.msgKey;
    }

    public void setMsgKey(long msgKey) {
        this.msgKey = msgKey;
    }
}
