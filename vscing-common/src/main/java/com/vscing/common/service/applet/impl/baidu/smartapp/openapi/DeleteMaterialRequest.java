package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class DeleteMaterialRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public DeleteMaterialRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("app_id")
    private long appId = 0;

    public long getAppID() {
        return this.appId;
    }

    public DeleteMaterialRequest setAppID(long appId) {
        this.appId = appId;
        return this;
    }

    @SerializedName("id")
    private long id = 0;

    public long getID() {
        return this.id;
    }

    public DeleteMaterialRequest setID(long id) {
        this.id = id;
        return this;
    }

    @SerializedName("path")
    private String path = "";

    public String getPath() {
        return this.path;
    }

    public DeleteMaterialRequest setPath(String path) {
        this.path = path;
        return this;
    }
}
