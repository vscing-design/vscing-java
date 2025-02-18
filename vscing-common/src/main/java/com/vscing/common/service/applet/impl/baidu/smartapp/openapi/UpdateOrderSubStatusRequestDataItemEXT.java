package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class UpdateOrderSubStatusRequestDataItemEXT {
    @SerializedName("SubsOrder")
    private UpdateOrderSubStatusRequestDataItemEXTSubsOrder subsOrder;

    public UpdateOrderSubStatusRequestDataItemEXTSubsOrder getSubsOrder() {
        return this.subsOrder;
    }

    public void setSubsOrder(UpdateOrderSubStatusRequestDataItemEXTSubsOrder subsOrder) {
        this.subsOrder = subsOrder;
    }
}
