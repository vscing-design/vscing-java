package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class CheckImageResponsedata {

    @SerializedName("res")
    private CheckImageResponsedataresItem[] res;

    public CheckImageResponsedataresItem[] getRes() {
        return this.res;
    }

    public void setRes(CheckImageResponsedataresItem[] res) {
        this.res = res;
    }

    @SerializedName("retrieveId")
    private String retrieveId;

    public String getRetrieveID() {
        return this.retrieveId;
    }

    public void setRetrieveID(String retrieveId) {
        this.retrieveId = retrieveId;
    }
}
