package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class SubmitSkuRequestPostBodyItemPriceInfo {
    @SerializedName("org_unit")
    private String orgUnit;

    @SerializedName("org_price")
    private String orgPrice;

    @SerializedName("real_price")
    private String realPrice;

    @SerializedName("range_min_price")
    private String rangeMinPrice;

    @SerializedName("range_max_price")
    private String rangeMaxPrice;

    public String getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(String orgUnit) {
        this.orgUnit = orgUnit;
    }

    public String getOrgPrice() {
        return orgPrice;
    }

    public void setOrgPrice(String orgPrice) {
        this.orgPrice = orgPrice;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }

    public String getRangeMinPrice() {
        return rangeMinPrice;
    }

    public void setRangeMinPrice(String rangeMinPrice) {
        this.rangeMinPrice = rangeMinPrice;
    }

    public String getRangeMaxPrice() {
        return rangeMaxPrice;
    }

    public void setRangeMaxPrice(String rangeMaxPrice) {
        this.rangeMaxPrice = rangeMaxPrice;
    }
}
