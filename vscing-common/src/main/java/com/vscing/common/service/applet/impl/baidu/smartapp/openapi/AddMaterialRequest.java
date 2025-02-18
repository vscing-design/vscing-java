package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddMaterialRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public AddMaterialRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("app_id")
    private long appId = 0;

    public long getAppID() {
        return this.appId;
    }

    public AddMaterialRequest setAppID(long appId) {
        this.appId = appId;
        return this;
    }

    @SerializedName("imageUrl")
    private String imageUrl = "";

    public String getImageURL() {
        return this.imageUrl;
    }

    public AddMaterialRequest setImageURL(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @SerializedName("title")
    private String title = "";

    public String getTitle() {
        return this.title;
    }

    public AddMaterialRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    @SerializedName("path")
    private String path = "";

    public String getPath() {
        return this.path;
    }

    public AddMaterialRequest setPath(String path) {
        this.path = path;
        return this;
    }

    @SerializedName("category1Code")
    private String category1Code = "";

    public String getCategory1Code() {
        return this.category1Code;
    }

    public AddMaterialRequest setCategory1Code(String category1Code) {
        this.category1Code = category1Code;
        return this;
    }

    @SerializedName("category2Code")
    private String category2Code = "";

    public String getCategory2Code() {
        return this.category2Code;
    }

    public AddMaterialRequest setCategory2Code(String category2Code) {
        this.category2Code = category2Code;
        return this;
    }

    @SerializedName("desc")
    private String desc = "";

    public String getDesc() {
        return this.desc;
    }

    public AddMaterialRequest setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    @SerializedName("labelAttr")
    private String labelAttr = "";

    public String getLabelAttr() {
        return this.labelAttr;
    }

    public AddMaterialRequest setLabelAttr(String labelAttr) {
        this.labelAttr = labelAttr;
        return this;
    }

    @SerializedName("labelDiscount")
    private String labelDiscount = "";

    public String getLabelDiscount() {
        return this.labelDiscount;
    }

    public AddMaterialRequest setLabelDiscount(String labelDiscount) {
        this.labelDiscount = labelDiscount;
        return this;
    }

    @SerializedName("buttonName")
    private String buttonName = "";

    public String getButtonName() {
        return this.buttonName;
    }

    public AddMaterialRequest setButtonName(String buttonName) {
        this.buttonName = buttonName;
        return this;
    }

    @SerializedName("bigImage")
    private String bigImage = "";

    public String getBigImage() {
        return this.bigImage;
    }

    public AddMaterialRequest setBigImage(String bigImage) {
        this.bigImage = bigImage;
        return this;
    }

    @SerializedName("verticalImage")
    private String verticalImage = "";

    public String getVerticalImage() {
        return this.verticalImage;
    }

    public AddMaterialRequest setVerticalImage(String verticalImage) {
        this.verticalImage = verticalImage;
        return this;
    }

    @SerializedName("extJson")
    private String extJson = "";

    public String getExtJSON() {
        return this.extJson;
    }

    public AddMaterialRequest setExtJSON(String extJson) {
        this.extJson = extJson;
        return this;
    }
}
