package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

public class SubmitSkuRequest {

    private String accessToken = "";
    private SubmitSkuRequestPostBodyItem[] submitSkuRequestPostBodyItem;

    public String getAccessToken() {
        return this.accessToken;
    }

    public SubmitSkuRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public SubmitSkuRequestPostBodyItem[] getSubmitSkuRequestPostBodyItem() {
        return submitSkuRequestPostBodyItem;
    }

    public void setSubmitSkuRequestPostBodyItem(SubmitSkuRequestPostBodyItem[] submitSkuRequestPostBodyItem) {
        this.submitSkuRequestPostBodyItem = submitSkuRequestPostBodyItem;
    }
}
