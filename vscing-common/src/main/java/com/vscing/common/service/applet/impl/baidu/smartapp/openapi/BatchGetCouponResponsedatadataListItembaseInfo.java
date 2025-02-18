package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class BatchGetCouponResponsedatadataListItembaseInfo {
    @SerializedName("color")
    private String color;

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @SerializedName("dateInfo")
    private BatchGetCouponResponsedatadataListItembaseInfodateInfo dateInfo;

    public BatchGetCouponResponsedatadataListItembaseInfodateInfo getDateInfo() {
        return this.dateInfo;
    }

    public void setDateInfo(BatchGetCouponResponsedatadataListItembaseInfodateInfo dateInfo) {
        this.dateInfo = dateInfo;
    }

    @SerializedName("getLimit")
    private long getLimit;

    public long getGetLimit() {
        return this.getLimit;
    }

    public void setGetLimit(long getLimit) {
        this.getLimit = getLimit;
    }

    @SerializedName("title")
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
