package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetTemplateLibraryListRequest {
    @SerializedName("access_token")
    private String accessToken = "";

    public String getAccessToken() {
        return this.accessToken;
    }

    public GetTemplateLibraryListRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @SerializedName("offset")
    private long offset = 0;

    public long getOffset() {
        return this.offset;
    }

    public GetTemplateLibraryListRequest setOffset(long offset) {
        this.offset = offset;
        return this;
    }

    @SerializedName("count")
    private long count = 0;

    public long getCount() {
        return this.count;
    }

    public GetTemplateLibraryListRequest setCount(long count) {
        this.count = count;
        return this;
    }
}
