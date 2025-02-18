package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class SubmitOrderLogisticsRequestDataItemEXTMainOrder {
    @SerializedName("Express")
    private SubmitOrderLogisticsRequestDataItemEXTMainOrderExpress express;

    public SubmitOrderLogisticsRequestDataItemEXTMainOrderExpress getExpress() {
        return this.express;
    }

    public void setExpress(SubmitOrderLogisticsRequestDataItemEXTMainOrderExpress express) {
        this.express = express;
    }
}
