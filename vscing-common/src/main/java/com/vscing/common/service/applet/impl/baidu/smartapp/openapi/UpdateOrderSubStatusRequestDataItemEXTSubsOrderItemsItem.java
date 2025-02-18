package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class UpdateOrderSubStatusRequestDataItemEXTSubsOrderItemsItem {
    @SerializedName("SubOrderID")
    private String subOrderId;

    public String getSubOrderID() {
        return this.subOrderId;
    }

    public void setSubOrderID(String subOrderId) {
        this.subOrderId = subOrderId;
    }

    @SerializedName("SubStatus")
    private long subStatus;

    public long getSubStatus() {
        return this.subStatus;
    }

    public void setSubStatus(long subStatus) {
        this.subStatus = subStatus;
    }
}
