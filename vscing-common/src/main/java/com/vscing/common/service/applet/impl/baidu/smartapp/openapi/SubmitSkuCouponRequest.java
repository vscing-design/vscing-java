package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

public class SubmitSkuCouponRequest {

    private String accessToken = "";
    private SubmitSkuCouponRequestPostBodyItem[] submitSkuCouponRequestPostBodyItem;

    public String getAccessToken() {
        return this.accessToken;
    }

    public SubmitSkuCouponRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public SubmitSkuCouponRequestPostBodyItem[] getSubmitSkuCouponRequestPostBodyItem() {
        return submitSkuCouponRequestPostBodyItem;
    }

    public void setSubmitSkuCouponRequestPostBodyItem(
        SubmitSkuCouponRequestPostBodyItem[] submitSkuCouponRequestPostBodyItem) {
        this.submitSkuCouponRequestPostBodyItem = submitSkuCouponRequestPostBodyItem;
    }
}
