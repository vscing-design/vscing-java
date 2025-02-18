package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund {
    @SerializedName("Amount")
    private long amount;

    public long getAmount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @SerializedName("Product")
    private UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] product;

    public UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] getProduct() {
        return this.product;
    }

    public void setProduct(UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] product) {
        this.product = product;
    }
}
