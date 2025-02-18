package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class CreateCouponRequestbaseInfo {
    @SerializedName("appRedirectPath")
    private String appRedirectPath;

    public String getAppRedirectPath() {
        return this.appRedirectPath;
    }

    public void setAppRedirectPath(String appRedirectPath) {
        this.appRedirectPath = appRedirectPath;
    }

    @SerializedName("codeType")
    private long codeType;

    public long getCodeType() {
        return this.codeType;
    }

    public void setCodeType(long codeType) {
        this.codeType = codeType;
    }

    @SerializedName("color")
    private String color;

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @SerializedName("dateInfo")
    private CreateCouponRequestbaseInfodateInfo dateInfo;

    public CreateCouponRequestbaseInfodateInfo getDateInfo() {
        return this.dateInfo;
    }

    public void setDateInfo(CreateCouponRequestbaseInfodateInfo dateInfo) {
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

    @SerializedName("quantity")
    private long quantity;

    public long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
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
