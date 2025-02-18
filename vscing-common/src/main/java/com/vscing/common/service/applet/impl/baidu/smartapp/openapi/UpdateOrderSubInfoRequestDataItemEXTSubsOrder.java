package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class UpdateOrderSubInfoRequestDataItemEXTSubsOrder {

    @SerializedName("Items")
    private UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem[] items;

    public UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem[] getItems() {
        return this.items;
    }

    public void setItems(UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem[] items) {
        this.items = items;
    }

    @SerializedName("Status")
    private long status;

    public long getStatus() {
        return this.status;
    }

    public void setStatus(long status) {
        this.status = status;
    }
}
