package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund {
    @SerializedName("Amount")
    private String amount;

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @SerializedName("Product")
    private UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] product;

    public UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] getProduct() {
        return this.product;
    }

    public void setProduct(UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] product) {
        this.product = product;
    }
}
