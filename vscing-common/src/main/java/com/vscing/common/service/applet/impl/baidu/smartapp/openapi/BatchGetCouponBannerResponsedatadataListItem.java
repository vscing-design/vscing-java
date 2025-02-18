package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class BatchGetCouponBannerResponsedatadataListItem {
    @SerializedName("appRedirectPath")
    private String appRedirectPath;

    public String getAppRedirectPath() {
        return this.appRedirectPath;
    }

    public void setAppRedirectPath(String appRedirectPath) {
        this.appRedirectPath = appRedirectPath;
    }

    @SerializedName("bannerId")
    private long bannerId;

    public long getBannerID() {
        return this.bannerId;
    }

    public void setBannerID(long bannerId) {
        this.bannerId = bannerId;
    }

    @SerializedName("couponId")
    private String couponId;

    public String getCouponID() {
        return this.couponId;
    }

    public void setCouponID(String couponId) {
        this.couponId = couponId;
    }

    @SerializedName("createTime")
    private long createTime;

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @SerializedName("picUrl")
    private String picUrl;

    public String getPicURL() {
        return this.picUrl;
    }

    public void setPicURL(String picUrl) {
        this.picUrl = picUrl;
    }

    @SerializedName("title")
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SerializedName("updateTime")
    private long updateTime;

    public long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
