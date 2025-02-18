package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetTemplateLibraryListResponsedata {

    @SerializedName("list")
    private GetTemplateLibraryListResponsedatalistItem[] list;

    public GetTemplateLibraryListResponsedatalistItem[] getList() {
        return this.list;
    }

    public void setList(GetTemplateLibraryListResponsedatalistItem[] list) {
        this.list = list;
    }

    @SerializedName("total_count")
    private long totalCount;

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
