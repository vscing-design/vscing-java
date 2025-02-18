package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class BatchGetCouponResponsedata {

    @SerializedName("dataList")
    private BatchGetCouponResponsedatadataListItem[] dataList;

    public BatchGetCouponResponsedatadataListItem[] getDataList() {
        return this.dataList;
    }

    public void setDataList(BatchGetCouponResponsedatadataListItem[] dataList) {
        this.dataList = dataList;
    }

    @SerializedName("pageNo")
    private long pageNo;

    public long getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    @SerializedName("total")
    private long total;

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
