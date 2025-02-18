package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class ApplyOrderRefundRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public ApplyOrderRefundRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("applyRefundMoney")
    private long applyRefundMoney = 0;

    public long getApplyRefundMoney() {
        return this.applyRefundMoney;
    }

    public ApplyOrderRefundRequest setApplyRefundMoney(long applyRefundMoney) {
        this.applyRefundMoney = applyRefundMoney;
        return this;
    }

    @SerializedName("bizRefundBatchId")
    private String bizRefundBatchId = "";

    public String getBizRefundBatchID() {
        return this.bizRefundBatchId;
    }

    public ApplyOrderRefundRequest setBizRefundBatchID(String bizRefundBatchId) {
        this.bizRefundBatchId = bizRefundBatchId;
        return this;
    }

    @SerializedName("isSkipAudit")
    private long isSkipAudit = 0;

    public long getIsSkipAudit() {
        return this.isSkipAudit;
    }

    public ApplyOrderRefundRequest setIsSkipAudit(long isSkipAudit) {
        this.isSkipAudit = isSkipAudit;
        return this;
    }

    @SerializedName("orderId")
    private long orderId = 0;

    public long getOrderID() {
        return this.orderId;
    }

    public ApplyOrderRefundRequest setOrderID(long orderId) {
        this.orderId = orderId;
        return this;
    }

    @SerializedName("refundReason")
    private String refundReason = "";

    public String getRefundReason() {
        return this.refundReason;
    }

    public ApplyOrderRefundRequest setRefundReason(String refundReason) {
        this.refundReason = refundReason;
        return this;
    }

    @SerializedName("refundType")
    private long refundType = 0;

    public long getRefundType() {
        return this.refundType;
    }

    public ApplyOrderRefundRequest setRefundType(long refundType) {
        this.refundType = refundType;
        return this;
    }

    @SerializedName("tpOrderId")
    private String tpOrderId = "";

    public String getTpOrderID() {
        return this.tpOrderId;
    }

    public ApplyOrderRefundRequest setTpOrderID(String tpOrderId) {
        this.tpOrderId = tpOrderId;
        return this;
    }

    @SerializedName("userId")
    private long userId = 0;

    public long getUserID() {
        return this.userId;
    }

    public ApplyOrderRefundRequest setUserID(long userId) {
        this.userId = userId;
        return this;
    }

    @SerializedName("refundNotifyUrl")
    private String refundNotifyUrl = "";

    public String getRefundNotifyURL() {
        return this.refundNotifyUrl;
    }

    public ApplyOrderRefundRequest setRefundNotifyURL(String refundNotifyUrl) {
        this.refundNotifyUrl = refundNotifyUrl;
        return this;
    }

    @SerializedName("pmAppKey")
    private String pmAppKey = "";

    public String getPmAppKey() {
        return this.pmAppKey;
    }

    public ApplyOrderRefundRequest setPmAppKey(String pmAppKey) {
        this.pmAppKey = pmAppKey;
        return this;
    }
}
