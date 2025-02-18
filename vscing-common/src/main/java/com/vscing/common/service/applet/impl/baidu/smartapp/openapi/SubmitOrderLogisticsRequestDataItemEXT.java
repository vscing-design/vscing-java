package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class SubmitOrderLogisticsRequestDataItemEXT {
    @SerializedName("MainOrder")
    private SubmitOrderLogisticsRequestDataItemEXTMainOrder mainOrder;

    public SubmitOrderLogisticsRequestDataItemEXTMainOrder getMainOrder() {
        return this.mainOrder;
    }

    public void setMainOrder(SubmitOrderLogisticsRequestDataItemEXTMainOrder mainOrder) {
        this.mainOrder = mainOrder;
    }
}
