package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetCommentListResponsedata {

    @SerializedName("list")
    private GetCommentListResponsedatalistItem[] list;

    public GetCommentListResponsedatalistItem[] getList() {
        return this.list;
    }

    public void setList(GetCommentListResponsedatalistItem[] list) {
        this.list = list;
    }
}
