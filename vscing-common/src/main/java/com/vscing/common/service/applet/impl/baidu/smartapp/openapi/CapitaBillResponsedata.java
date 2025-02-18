package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class CapitaBillResponsedata {

    @SerializedName("data")
    private CapitaBillResponsedatadataItem[] data;

    public CapitaBillResponsedatadataItem[] getData() {
        return this.data;
    }

    public void setData(CapitaBillResponsedatadataItem[] data) {
        this.data = data;
    }

    @SerializedName("totalCount")
    private long totalCount;

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
