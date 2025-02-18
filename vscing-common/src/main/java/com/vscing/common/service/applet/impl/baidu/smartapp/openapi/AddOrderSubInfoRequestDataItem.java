package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddOrderSubInfoRequestDataItem {
    @SerializedName("BizAPPID")
    private String bizAPPId;

    public String getBizAPPID() {
        return this.bizAPPId;
    }

    public void setBizAPPID(String bizAPPId) {
        this.bizAPPId = bizAPPId;
    }

    @SerializedName("CateID")
    private long cateId;

    public long getCateID() {
        return this.cateId;
    }

    public void setCateID(long cateId) {
        this.cateId = cateId;
    }

    @SerializedName("EXT")
    private AddOrderSubInfoRequestDataItemEXT eXT;

    public AddOrderSubInfoRequestDataItemEXT getEXT() {
        return this.eXT;
    }

    public void setEXT(AddOrderSubInfoRequestDataItemEXT eXT) {
        this.eXT = eXT;
    }

    @SerializedName("ResourceID")
    private String resourceId;

    public String getResourceID() {
        return this.resourceId;
    }

    public void setResourceID(String resourceId) {
        this.resourceId = resourceId;
    }
}
