package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class FindOrderRefundResponsedataItem {
    @SerializedName("bizRefundBatchId")
    private String bizRefundBatchId;

    public String getBizRefundBatchID() {
        return this.bizRefundBatchId;
    }

    public void setBizRefundBatchID(String bizRefundBatchId) {
        this.bizRefundBatchId = bizRefundBatchId;
    }

    @SerializedName("orderId")
    private long orderId;

    public long getOrderID() {
        return this.orderId;
    }

    public void setOrderID(long orderId) {
        this.orderId = orderId;
    }

    @SerializedName("refundBatchId")
    private long refundBatchId;

    public long getRefundBatchID() {
        return this.refundBatchId;
    }

    public void setRefundBatchID(long refundBatchId) {
        this.refundBatchId = refundBatchId;
    }

    @SerializedName("refundStatus")
    private long refundStatus;

    public long getRefundStatus() {
        return this.refundStatus;
    }

    public void setRefundStatus(long refundStatus) {
        this.refundStatus = refundStatus;
    }

    @SerializedName("userId")
    private long userId;

    public long getUserID() {
        return this.userId;
    }

    public void setUserID(long userId) {
        this.userId = userId;
    }
}
