package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class UpdateOrderSubInfoRequestDataItemEXT {
    @SerializedName("SubsOrder")
    private UpdateOrderSubInfoRequestDataItemEXTSubsOrder subsOrder;

    public UpdateOrderSubInfoRequestDataItemEXTSubsOrder getSubsOrder() {
        return this.subsOrder;
    }

    public void setSubsOrder(UpdateOrderSubInfoRequestDataItemEXTSubsOrder subsOrder) {
        this.subsOrder = subsOrder;
    }
}
