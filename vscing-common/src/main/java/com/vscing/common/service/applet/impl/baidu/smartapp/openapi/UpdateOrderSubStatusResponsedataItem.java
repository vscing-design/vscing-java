package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class UpdateOrderSubStatusResponsedataItem {
    @SerializedName("biz_app_id")
    private String bizAppId;

    public String getBizAppID() {
        return this.bizAppId;
    }

    public void setBizAppID(String bizAppId) {
        this.bizAppId = bizAppId;
    }

    @SerializedName("cate_id")
    private String cateId;

    public String getCateID() {
        return this.cateId;
    }

    public void setCateID(String cateId) {
        this.cateId = cateId;
    }

    @SerializedName("resource_id")
    private String resourceId;

    public String getResourceID() {
        return this.resourceId;
    }

    public void setResourceID(String resourceId) {
        this.resourceId = resourceId;
    }

    @SerializedName("rows_affected")
    private String rowsAffected;

    public String getRowsAffected() {
        return this.rowsAffected;
    }

    public void setRowsAffected(String rowsAffected) {
        this.rowsAffected = rowsAffected;
    }
}
