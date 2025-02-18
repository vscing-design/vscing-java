package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class BatchGetCouponBannerResponsedata {

    @SerializedName("dataList")
    private BatchGetCouponBannerResponsedatadataListItem[] dataList;

    public BatchGetCouponBannerResponsedatadataListItem[] getDataList() {
        return this.dataList;
    }

    public void setDataList(BatchGetCouponBannerResponsedatadataListItem[] dataList) {
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
