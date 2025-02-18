package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund {
    @SerializedName("Amount")
    private long amount;

    public long getAmount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @SerializedName("Product")
    private AddOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] product;

    public AddOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] getProduct() {
        return this.product;
    }

    public void setProduct(AddOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] product) {
        this.product = product;
    }
}
