package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class SubmitResourceRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public SubmitResourceRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("app_id")
    private long appId = 0;

    public long getAppID() {
        return this.appId;
    }

    public SubmitResourceRequest setAppID(long appId) {
        this.appId = appId;
        return this;
    }

    @SerializedName("body")
    private String body = "";

    public String getBody() {
        return this.body;
    }

    public SubmitResourceRequest setBody(String body) {
        this.body = body;
        return this;
    }

    @SerializedName("ext")
    private String ext = "";

    public String getExt() {
        return this.ext;
    }

    public SubmitResourceRequest setExt(String ext) {
        this.ext = ext;
        return this;
    }

    @SerializedName("feed_sub_type")
    private String feedSubType = "";

    public String getFeedSubType() {
        return this.feedSubType;
    }

    public SubmitResourceRequest setFeedSubType(String feedSubType) {
        this.feedSubType = feedSubType;
        return this;
    }

    @SerializedName("feed_type")
    private String feedType = "";

    public String getFeedType() {
        return this.feedType;
    }

    public SubmitResourceRequest setFeedType(String feedType) {
        this.feedType = feedType;
        return this;
    }

    @SerializedName("images")
    private String images = "";

    public String getImages() {
        return this.images;
    }

    public SubmitResourceRequest setImages(String images) {
        this.images = images;
        return this;
    }

    @SerializedName("mapp_sub_type")
    private String mappSubType = "";

    public String getMappSubType() {
        return this.mappSubType;
    }

    public SubmitResourceRequest setMappSubType(String mappSubType) {
        this.mappSubType = mappSubType;
        return this;
    }

    @SerializedName("mapp_type")
    private String mappType = "";

    public String getMappType() {
        return this.mappType;
    }

    public SubmitResourceRequest setMappType(String mappType) {
        this.mappType = mappType;
        return this;
    }

    @SerializedName("path")
    private String path = "";

    public String getPath() {
        return this.path;
    }

    public SubmitResourceRequest setPath(String path) {
        this.path = path;
        return this;
    }

    @SerializedName("tags")
    private String tags = "";

    public String getTags() {
        return this.tags;
    }

    public SubmitResourceRequest setTags(String tags) {
        this.tags = tags;
        return this;
    }

    @SerializedName("title")
    private String title = "";

    public String getTitle() {
        return this.title;
    }

    public SubmitResourceRequest setTitle(String title) {
        this.title = title;
        return this;
    }
}
