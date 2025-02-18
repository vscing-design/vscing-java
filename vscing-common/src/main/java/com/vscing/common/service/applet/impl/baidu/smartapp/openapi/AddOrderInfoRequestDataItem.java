package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddOrderInfoRequestDataItem {
    @SerializedName("BizAPPID")
    private String bizAPPId;

    public String getBizAPPID() {
        return this.bizAPPId;
    }

    public void setBizAPPID(String bizAPPId) {
        this.bizAPPId = bizAPPId;
    }

    @SerializedName("CateID")
    private long cateId;

    public long getCateID() {
        return this.cateId;
    }

    public void setCateID(long cateId) {
        this.cateId = cateId;
    }

    @SerializedName("Ctime")
    private long ctime;

    public long getCtime() {
        return this.ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    @SerializedName("EXT")
    private AddOrderInfoRequestDataItemEXT eXT;

    public AddOrderInfoRequestDataItemEXT getEXT() {
        return this.eXT;
    }

    public void setEXT(AddOrderInfoRequestDataItemEXT eXT) {
        this.eXT = eXT;
    }

    @SerializedName("Mtime")
    private long mtime;

    public long getMtime() {
        return this.mtime;
    }

    public void setMtime(long mtime) {
        this.mtime = mtime;
    }

    @SerializedName("ResourceID")
    private String resourceId;

    public String getResourceID() {
        return this.resourceId;
    }

    public void setResourceID(String resourceId) {
        this.resourceId = resourceId;
    }

    @SerializedName("Status")
    private long status;

    public long getStatus() {
        return this.status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    @SerializedName("Title")
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
