package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class CheckTextResponsedata {

    @SerializedName("res")
    private CheckTextResponsedataresItem[] res;

    public CheckTextResponsedataresItem[] getRes() {
        return this.res;
    }

    public void setRes(CheckTextResponsedataresItem[] res) {
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
