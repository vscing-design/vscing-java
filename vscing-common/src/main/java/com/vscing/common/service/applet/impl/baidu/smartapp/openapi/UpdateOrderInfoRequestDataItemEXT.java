package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class UpdateOrderInfoRequestDataItemEXT {
    @SerializedName("MainOrder")
    private UpdateOrderInfoRequestDataItemEXTMainOrder mainOrder;

    public UpdateOrderInfoRequestDataItemEXTMainOrder getMainOrder() {
        return this.mainOrder;
    }

    public void setMainOrder(UpdateOrderInfoRequestDataItemEXTMainOrder mainOrder) {
        this.mainOrder = mainOrder;
    }

    @SerializedName("SubsOrder")
    private UpdateOrderInfoRequestDataItemEXTSubsOrder subsOrder;

    public UpdateOrderInfoRequestDataItemEXTSubsOrder getSubsOrder() {
        return this.subsOrder;
    }

    public void setSubsOrder(UpdateOrderInfoRequestDataItemEXTSubsOrder subsOrder) {
        this.subsOrder = subsOrder;
    }
}
