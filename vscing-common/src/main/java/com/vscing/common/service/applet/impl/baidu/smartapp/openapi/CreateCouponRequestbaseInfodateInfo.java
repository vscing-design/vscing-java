package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class CreateCouponRequestbaseInfodateInfo {
    @SerializedName("beginTimestamp")
    private long beginTimestamp;

    public long getBeginTimestamp() {
        return this.beginTimestamp;
    }

    public void setBeginTimestamp(long beginTimestamp) {
        this.beginTimestamp = beginTimestamp;
    }

    @SerializedName("endTimestamp")
    private long endTimestamp;

    public long getEndTimestamp() {
        return this.endTimestamp;
    }

    public void setEndTimestamp(long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    @SerializedName("getEndTimestamp")
    private long getEndTimestamp;

    public long getGetEndTimestamp() {
        return this.getEndTimestamp;
    }

    public void setGetEndTimestamp(long getEndTimestamp) {
        this.getEndTimestamp = getEndTimestamp;
    }

    @SerializedName("getStartTimestamp")
    private long getStartTimestamp;

    public long getGetStartTimestamp() {
        return this.getStartTimestamp;
    }

    public void setGetStartTimestamp(long getStartTimestamp) {
        this.getStartTimestamp = getStartTimestamp;
    }

    @SerializedName("timeUnit")
    private long timeUnit;

    public long getTimeUnit() {
        return this.timeUnit;
    }

    public void setTimeUnit(long timeUnit) {
        this.timeUnit = timeUnit;
    }

    @SerializedName("timeValue")
    private long timeValue;

    public long getTimeValue() {
        return this.timeValue;
    }

    public void setTimeValue(long timeValue) {
        this.timeValue = timeValue;
    }

    @SerializedName("type")
    private long type;

    public long getType() {
        return this.type;
    }

    public void setType(long type) {
        this.type = type;
    }
}
