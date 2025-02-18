package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddOrderInfoRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public AddOrderInfoRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("open_id")
    private String openId = "";

    public String getOpenID() {
        return this.openId;
    }

    public AddOrderInfoRequest setOpenID(String openId) {
        this.openId = openId;
        return this;
    }

    @SerializedName("swan_id")
    private String swanId = "";

    public String getSwanID() {
        return this.swanId;
    }

    public AddOrderInfoRequest setSwanID(String swanId) {
        this.swanId = swanId;
        return this;
    }

    @SerializedName("scene_id")
    private String sceneId = "";

    public String getSceneID() {
        return this.sceneId;
    }

    public AddOrderInfoRequest setSceneID(String sceneId) {
        this.sceneId = sceneId;
        return this;
    }

    @SerializedName("scene_type")
    private long sceneType = 0;

    public long getSceneType() {
        return this.sceneType;
    }

    public AddOrderInfoRequest setSceneType(long sceneType) {
        this.sceneType = sceneType;
        return this;
    }

    @SerializedName("pm_app_key")
    private String pmAppKey = "";

    public String getPmAppKey() {
        return this.pmAppKey;
    }

    public AddOrderInfoRequest setPmAppKey(String pmAppKey) {
        this.pmAppKey = pmAppKey;
        return this;
    }

    @SerializedName("Data")
    private AddOrderInfoRequestDataItem[] data;

    public AddOrderInfoRequestDataItem[] getData() {
        return this.data;
    }

    public AddOrderInfoRequest setData(AddOrderInfoRequestDataItem[] data) {
        this.data = data;
        return this;
    }
}
