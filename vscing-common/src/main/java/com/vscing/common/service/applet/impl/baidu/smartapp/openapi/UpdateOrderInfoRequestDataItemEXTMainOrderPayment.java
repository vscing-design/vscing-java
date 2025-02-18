package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class UpdateOrderInfoRequestDataItemEXTMainOrderPayment {
    @SerializedName("Amount")
    private long amount;

    public long getAmount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @SerializedName("IsPayment")
    private boolean isPayment;

    public boolean getIsPayment() {
        return this.isPayment;
    }

    public void setIsPayment(boolean isPayment) {
        this.isPayment = isPayment;
    }

    @SerializedName("Method")
    private long method;

    public long getMethod() {
        return this.method;
    }

    public void setMethod(long method) {
        this.method = method;
    }

    @SerializedName("PaymentInfo")
    private UpdateOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem[] paymentInfo;

    public UpdateOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem[] getPaymentInfo() {
        return this.paymentInfo;
    }

    public void setPaymentInfo(UpdateOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem[] paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    @SerializedName("PreferentialInfo")
    private UpdateOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem[] preferentialInfo;

    public UpdateOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem[] getPreferentialInfo() {
        return this.preferentialInfo;
    }

    public void setPreferentialInfo(
        UpdateOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem[] preferentialInfo) {
        this.preferentialInfo = preferentialInfo;
    }

    @SerializedName("Time")
    private long time;

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
