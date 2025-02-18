package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class BatchUploadCouponCodeResponsedata {
    @SerializedName("failNum")
    private String failNum;

    public String getFailNum() {
        return this.failNum;
    }

    public void setFailNum(String failNum) {
        this.failNum = failNum;
    }

    @SerializedName("successNum")
    private String successNum;

    public String getSuccessNum() {
        return this.successNum;
    }

    public void setSuccessNum(String successNum) {
        this.successNum = successNum;
    }
}
