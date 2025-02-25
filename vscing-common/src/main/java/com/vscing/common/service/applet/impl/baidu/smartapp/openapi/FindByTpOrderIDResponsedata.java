package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class FindByTpOrderIDResponsedata {
    @SerializedName("appId")
    private long appId;

    public long getAppID() {
        return this.appId;
    }

    public void setAppID(long appId) {
        this.appId = appId;
    }

    @SerializedName("appKey")
    private String appKey;

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @SerializedName("bizInfo")
    private String bizInfo; // 保持为 String 类型

    public String getBizInfo() {
        return this.bizInfo;
    }

    public void setBizInfo(String bizInfo) {
        this.bizInfo = bizInfo;
    }

    @SerializedName("count")
    private long count;

    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @SerializedName("createTime")
    private long createTime;

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @SerializedName("dealId")
    private long dealId;

    public long getDealID() {
        return this.dealId;
    }

    public void setDealID(long dealId) {
        this.dealId = dealId;
    }

    @SerializedName("openId")
    private String openId;

    public String getOpenID() {
        return this.openId;
    }

    public void setOpenID(String openId) {
        this.openId = openId;
    }

    @SerializedName("orderId")
    private long orderId;

    public long getOrderID() {
        return this.orderId;
    }

    public void setOrderID(long orderId) {
        this.orderId = orderId;
    }

    @SerializedName("oriPrice")
    private long oriPrice;

    public long getOriPrice() {
        return this.oriPrice;
    }

    public void setOriPrice(long oriPrice) {
        this.oriPrice = oriPrice;
    }

    @SerializedName("parentOrderId")
    private long parentOrderId;

    public long getParentOrderID() {
        return this.parentOrderId;
    }

    public void setParentOrderID(long parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    @SerializedName("parentType")
    private long parentType;

    public long getParentType() {
        return this.parentType;
    }

    public void setParentType(long parentType) {
        this.parentType = parentType;
    }

    @SerializedName("payMoney")
    private long payMoney;

    public long getPayMoney() {
        return this.payMoney;
    }

    public void setPayMoney(long payMoney) {
        this.payMoney = payMoney;
    }

    @SerializedName("settlePrice")
    private long settlePrice;

    public long getSettlePrice() {
        return this.settlePrice;
    }

    public void setSettlePrice(long settlePrice) {
        this.settlePrice = settlePrice;
    }

    @SerializedName("status")
    private long status;

    public long getStatus() {
        return this.status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    @SerializedName("subStatus")
    private long subStatus;

    public long getSubStatus() {
        return this.subStatus;
    }

    public void setSubStatus(long subStatus) {
        this.subStatus = subStatus;
    }

    @SerializedName("totalMoney")
    private long totalMoney;

    public long getTotalMoney() {
        return this.totalMoney;
    }

    public void setTotalMoney(long totalMoney) {
        this.totalMoney = totalMoney;
    }

    @SerializedName("tpId")
    private long tpId;

    public long getTpID() {
        return this.tpId;
    }

    public void setTpID(long tpId) {
        this.tpId = tpId;
    }

    @SerializedName("tpOrderId")
    private String tpOrderId;

    public String getTpOrderID() {
        return this.tpOrderId;
    }

    public void setTpOrderID(String tpOrderId) {
        this.tpOrderId = tpOrderId;
    }

    @SerializedName("tradeNo")
    private String tradeNo;

    public String getTradeNo() {
        return this.tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @SerializedName("type")
    private long type;

    public long getType() {
        return this.type;
    }

    public void setType(long type) {
        this.type = type;
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
