package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class DetectRiskResponsedata {
    @SerializedName("level")
    private String level;

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @SerializedName("tag")
    private String[] tag;

    public String[] getTag() {
        return this.tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }
}
