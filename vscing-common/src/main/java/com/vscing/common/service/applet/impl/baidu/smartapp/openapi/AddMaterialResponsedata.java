package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddMaterialResponsedata {
    @SerializedName("id")
    private long id;

    public long getID() {
        return this.id;
    }

    public void setID(long id) {
        this.id = id;
    }

    @SerializedName("path")
    private String path;

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
