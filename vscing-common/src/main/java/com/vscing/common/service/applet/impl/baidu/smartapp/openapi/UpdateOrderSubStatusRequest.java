package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class UpdateOrderSubStatusRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public UpdateOrderSubStatusRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("open_id")
    private String openId = "";

    public String getOpenID() {
        return this.openId;
    }

    public UpdateOrderSubStatusRequest setOpenID(String openId) {
        this.openId = openId;
        return this;
    }

    @SerializedName("scene_id")
    private String sceneId = "";

    public String getSceneID() {
        return this.sceneId;
    }

    public UpdateOrderSubStatusRequest setSceneID(String sceneId) {
        this.sceneId = sceneId;
        return this;
    }

    @SerializedName("scene_type")
    private long sceneType = 0;

    public long getSceneType() {
        return this.sceneType;
    }

    public UpdateOrderSubStatusRequest setSceneType(long sceneType) {
        this.sceneType = sceneType;
        return this;
    }

    @SerializedName("pm_app_key")
    private String pmAppKey = "";

    public String getPmAppKey() {
        return this.pmAppKey;
    }

    public UpdateOrderSubStatusRequest setPmAppKey(String pmAppKey) {
        this.pmAppKey = pmAppKey;
        return this;
    }

    @SerializedName("Data")
    private UpdateOrderSubStatusRequestDataItem[] data;

    public UpdateOrderSubStatusRequestDataItem[] getData() {
        return this.data;
    }

    public UpdateOrderSubStatusRequest setData(UpdateOrderSubStatusRequestDataItem[] data) {
        this.data = data;
        return this;
    }
}
