package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddOrderSubInfoRequestDataItemEXTSubsOrder {

    @SerializedName("Items")
    private AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItem[] items;

    public AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItem[] getItems() {
        return this.items;
    }

    public void setItems(AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItem[] items) {
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
