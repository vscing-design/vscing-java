package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class OrderBillResponsedatadataItem {
    @SerializedName("createTime")
    private String createTime;

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @SerializedName("downloadName")
    private String downloadName;

    public String getDownloadName() {
        return this.downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    @SerializedName("exportStatus")
    private long exportStatus;

    public long getExportStatus() {
        return this.exportStatus;
    }

    public void setExportStatus(long exportStatus) {
        this.exportStatus = exportStatus;
    }

    @SerializedName("url")
    private String url;

    public String getURL() {
        return this.url;
    }

    public void setURL(String url) {
        this.url = url;
    }
}
