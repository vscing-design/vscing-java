package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem {
    @SerializedName("Amount")
    private long amount;

    public long getAmount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @SerializedName("ID")
    private String id;

    public String getID() {
        return this.id;
    }

    public void setID(String id) {
        this.id = id;
    }

    @SerializedName("Quantity")
    private long quantity;

    public long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
