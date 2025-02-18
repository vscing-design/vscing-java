package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class SubmitSkuRequestPostBodyItemActivityInfoItem {
    @SerializedName("activity_type")
    private String activityType;

    @SerializedName("activity_desc")
    private String activityDesc;

    @SerializedName("activity_path")
    private String activityPath;

    @SerializedName("activity_start_time")
    private long activityStartTime;

    @SerializedName("activity_end_time")
    private long activityEndTime;

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public String getActivityPath() {
        return activityPath;
    }

    public void setActivityPath(String activityPath) {
        this.activityPath = activityPath;
    }

    public long getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(long activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public long getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(long activityEndTime) {
        this.activityEndTime = activityEndTime;
    }
}
