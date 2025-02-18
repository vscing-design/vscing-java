package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItem {
    @SerializedName("CTime")
    private long cTime;

    public long getCTime() {
        return this.cTime;
    }

    public void setCTime(long cTime) {
        this.cTime = cTime;
    }

    @SerializedName("MTime")
    private long mTime;

    public long getMTime() {
        return this.mTime;
    }

    public void setMTime(long mTime) {
        this.mTime = mTime;
    }

    @SerializedName("OrderDetail")
    private UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail orderDetail;

    public UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail getOrderDetail() {
        return this.orderDetail;
    }

    public void setOrderDetail(UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    @SerializedName("OrderType")
    private long orderType;

    public long getOrderType() {
        return this.orderType;
    }

    public void setOrderType(long orderType) {
        this.orderType = orderType;
    }

    @SerializedName("Refund")
    private UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund refund;

    public UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund getRefund() {
        return this.refund;
    }

    public void setRefund(UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund refund) {
        this.refund = refund;
    }

    @SerializedName("SubOrderID")
    private String subOrderId;

    public String getSubOrderID() {
        return this.subOrderId;
    }

    public void setSubOrderID(String subOrderId) {
        this.subOrderId = subOrderId;
    }

    @SerializedName("SubStatus")
    private String subStatus;

    public String getSubStatus() {
        return this.subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }
}
