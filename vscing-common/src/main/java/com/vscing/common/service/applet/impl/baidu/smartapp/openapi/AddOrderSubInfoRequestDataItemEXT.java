package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddOrderSubInfoRequestDataItemEXT {
    @SerializedName("SubsOrder")
    private AddOrderSubInfoRequestDataItemEXTSubsOrder subsOrder;

    public AddOrderSubInfoRequestDataItemEXTSubsOrder getSubsOrder() {
        return this.subsOrder;
    }

    public void setSubsOrder(AddOrderSubInfoRequestDataItemEXTSubsOrder subsOrder) {
        this.subsOrder = subsOrder;
    }
}
