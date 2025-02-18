package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund {
    @SerializedName("Amount")
    private String amount;

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @SerializedName("Product")
    private AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] product;

    public AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] getProduct() {
        return this.product;
    }

    public void setProduct(AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] product) {
        this.product = product;
    }
}
