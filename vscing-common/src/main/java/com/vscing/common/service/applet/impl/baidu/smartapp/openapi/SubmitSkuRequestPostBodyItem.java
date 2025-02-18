package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class SubmitSkuRequestPostBodyItem {
    @SerializedName("path")
    private String path;

    @SerializedName("trade_type")
    private int tradeType;

    @SerializedName("title")
    private String title;

    @SerializedName("desc")
    private String desc;

    @SerializedName("region")
    private String region;

    @SerializedName("tag")
    private String tag;

    @SerializedName("button_name")
    private String buttonName;

    @SerializedName("schema")
    private String schema;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public SubmitSkuRequestPostBodyItemPriceInfo getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(SubmitSkuRequestPostBodyItemPriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    public SubmitSkuRequestPostBodyItemActivityInfoItem[] getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(SubmitSkuRequestPostBodyItemActivityInfoItem[] activityInfo) {
        this.activityInfo = activityInfo;
    }

    @SerializedName("images")
    private String[] images;

    @SerializedName("price_info")
    private SubmitSkuRequestPostBodyItemPriceInfo priceInfo;

    private SubmitSkuRequestPostBodyItemActivityInfoItem[] activityInfo;
}
