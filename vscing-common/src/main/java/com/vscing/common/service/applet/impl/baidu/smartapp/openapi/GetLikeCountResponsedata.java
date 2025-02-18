package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetLikeCountResponsedata {
    @SerializedName("like_count")
    private long likeCount;

    public long getLikeCount() {
        return this.likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }
}
