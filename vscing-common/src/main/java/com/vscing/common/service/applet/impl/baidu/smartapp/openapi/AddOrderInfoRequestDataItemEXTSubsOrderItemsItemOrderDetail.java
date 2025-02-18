package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail {
    @SerializedName("Name")
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("Status")
    private long status;

    public long getStatus() {
        return this.status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    @SerializedName("SwanSchema")
    private String swanSchema;

    public String getSwanSchema() {
        return this.swanSchema;
    }

    public void setSwanSchema(String swanSchema) {
        this.swanSchema = swanSchema;
    }
}
