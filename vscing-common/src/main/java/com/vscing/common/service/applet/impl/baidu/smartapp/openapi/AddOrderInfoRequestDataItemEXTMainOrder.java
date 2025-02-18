package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddOrderInfoRequestDataItemEXTMainOrder {
    @SerializedName("Appraise")
    private AddOrderInfoRequestDataItemEXTMainOrderAppraise appraise;

    public AddOrderInfoRequestDataItemEXTMainOrderAppraise getAppraise() {
        return this.appraise;
    }

    public void setAppraise(AddOrderInfoRequestDataItemEXTMainOrderAppraise appraise) {
        this.appraise = appraise;
    }

    @SerializedName("OrderDetail")
    private AddOrderInfoRequestDataItemEXTMainOrderOrderDetail orderDetail;

    public AddOrderInfoRequestDataItemEXTMainOrderOrderDetail getOrderDetail() {
        return this.orderDetail;
    }

    public void setOrderDetail(AddOrderInfoRequestDataItemEXTMainOrderOrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    @SerializedName("Payment")
    private AddOrderInfoRequestDataItemEXTMainOrderPayment payment;

    public AddOrderInfoRequestDataItemEXTMainOrderPayment getPayment() {
        return this.payment;
    }

    public void setPayment(AddOrderInfoRequestDataItemEXTMainOrderPayment payment) {
        this.payment = payment;
    }

    @SerializedName("Products")
    private AddOrderInfoRequestDataItemEXTMainOrderProductsItem[] products;

    public AddOrderInfoRequestDataItemEXTMainOrderProductsItem[] getProducts() {
        return this.products;
    }

    public void setProducts(AddOrderInfoRequestDataItemEXTMainOrderProductsItem[] products) {
        this.products = products;
    }
}
