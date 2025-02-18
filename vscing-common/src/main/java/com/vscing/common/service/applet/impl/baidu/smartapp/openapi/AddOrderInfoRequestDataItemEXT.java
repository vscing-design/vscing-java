package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddOrderInfoRequestDataItemEXT {
    @SerializedName("MainOrder")
    private AddOrderInfoRequestDataItemEXTMainOrder mainOrder;

    public AddOrderInfoRequestDataItemEXTMainOrder getMainOrder() {
        return this.mainOrder;
    }

    public void setMainOrder(AddOrderInfoRequestDataItemEXTMainOrder mainOrder) {
        this.mainOrder = mainOrder;
    }

    @SerializedName("SubsOrder")
    private AddOrderInfoRequestDataItemEXTSubsOrder subsOrder;

    public AddOrderInfoRequestDataItemEXTSubsOrder getSubsOrder() {
        return this.subsOrder;
    }

    public void setSubsOrder(AddOrderInfoRequestDataItemEXTSubsOrder subsOrder) {
        this.subsOrder = subsOrder;
    }
}
