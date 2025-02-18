package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItem {
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
    private AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail orderDetail;

    public AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail getOrderDetail() {
        return this.orderDetail;
    }

    public void setOrderDetail(AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail orderDetail) {
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
    private AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund refund;

    public AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund getRefund() {
        return this.refund;
    }

    public void setRefund(AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund refund) {
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
    private long subStatus;

    public long getSubStatus() {
        return this.subStatus;
    }

    public void setSubStatus(long subStatus) {
        this.subStatus = subStatus;
    }
}
