package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class SubmitOrderLogisticsRequestDataItemEXTMainOrderExpress {
    @SerializedName("Code")
    private String code;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @SerializedName("ID")
    private String id;

    public String getID() {
        return this.id;
    }

    public void setID(String id) {
        this.id = id;
    }

    @SerializedName("Name")
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("Status")
    private long status;

    public long getStatus() {
        return this.status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    @SerializedName("Type")
    private long type;

    public long getType() {
        return this.type;
    }

    public void setType(long type) {
        this.type = type;
    }
}
