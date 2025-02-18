package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class UpdateOrderInfoRequestDataItemEXTMainOrder {
    @SerializedName("Appraise")
    private UpdateOrderInfoRequestDataItemEXTMainOrderAppraise appraise;

    public UpdateOrderInfoRequestDataItemEXTMainOrderAppraise getAppraise() {
        return this.appraise;
    }

    public void setAppraise(UpdateOrderInfoRequestDataItemEXTMainOrderAppraise appraise) {
        this.appraise = appraise;
    }

    @SerializedName("OrderDetail")
    private UpdateOrderInfoRequestDataItemEXTMainOrderOrderDetail orderDetail;

    public UpdateOrderInfoRequestDataItemEXTMainOrderOrderDetail getOrderDetail() {
        return this.orderDetail;
    }

    public void setOrderDetail(UpdateOrderInfoRequestDataItemEXTMainOrderOrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    @SerializedName("Payment")
    private UpdateOrderInfoRequestDataItemEXTMainOrderPayment payment;

    public UpdateOrderInfoRequestDataItemEXTMainOrderPayment getPayment() {
        return this.payment;
    }

    public void setPayment(UpdateOrderInfoRequestDataItemEXTMainOrderPayment payment) {
        this.payment = payment;
    }

    @SerializedName("Products")
    private UpdateOrderInfoRequestDataItemEXTMainOrderProductsItem[] products;

    public UpdateOrderInfoRequestDataItemEXTMainOrderProductsItem[] getProducts() {
        return this.products;
    }

    public void setProducts(UpdateOrderInfoRequestDataItemEXTMainOrderProductsItem[] products) {
        this.products = products;
    }
}
