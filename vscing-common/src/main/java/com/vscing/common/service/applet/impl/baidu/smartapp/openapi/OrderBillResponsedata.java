package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class OrderBillResponsedata {

    @SerializedName("data")
    private OrderBillResponsedatadataItem[] data;

    public OrderBillResponsedatadataItem[] getData() {
        return this.data;
    }

    public void setData(OrderBillResponsedatadataItem[] data) {
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
