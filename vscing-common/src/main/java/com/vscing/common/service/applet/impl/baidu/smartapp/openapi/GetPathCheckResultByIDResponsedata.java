package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetPathCheckResultByIDResponsedata {
    @SerializedName("checkStatus")
    private long checkStatus;

    public long getCheckStatus() {
        return this.checkStatus;
    }

    public void setCheckStatus(long checkStatus) {
        this.checkStatus = checkStatus;
    }

    @SerializedName("createTime")
    private long createTime;

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @SerializedName("detail")
    private GetPathCheckResultByIDResponsedatadetailItem[] detail;

    public GetPathCheckResultByIDResponsedatadetailItem[] getDetail() {
        return this.detail;
    }

    public void setDetail(GetPathCheckResultByIDResponsedatadetailItem[] detail) {
        this.detail = detail;
    }
}
