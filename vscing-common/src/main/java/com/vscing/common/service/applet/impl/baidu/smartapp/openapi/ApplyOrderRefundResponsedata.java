package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class ApplyOrderRefundResponsedata {
    @SerializedName("refundBatchId")
    private String refundBatchId;

    public String getRefundBatchID() {
        return this.refundBatchId;
    }

    public void setRefundBatchID(String refundBatchId) {
        this.refundBatchId = refundBatchId;
    }

    @SerializedName("refundPayMoney")
    private long refundPayMoney;

    public long getRefundPayMoney() {
        return this.refundPayMoney;
    }

    public void setRefundPayMoney(long refundPayMoney) {
        this.refundPayMoney = refundPayMoney;
    }
}
