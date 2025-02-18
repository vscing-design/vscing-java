package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class AddOrderInfoRequestDataItemEXTMainOrderProductsItem {
    @SerializedName("Desc")
    private String desc;

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @SerializedName("ID")
    private String id;

    public String getID() {
        return this.id;
    }

    public void setID(String id) {
        this.id = id;
    }

    @SerializedName("ImgList")
    private String[] imgList;

    public String[] getImgList() {
        return this.imgList;
    }

    public void setImgList(String[] imgList) {
        this.imgList = imgList;
    }

    @SerializedName("Name")
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("PayPrice")
    private long payPrice;

    public long getPayPrice() {
        return this.payPrice;
    }

    public void setPayPrice(long payPrice) {
        this.payPrice = payPrice;
    }

    @SerializedName("Price")
    private long price;

    public long getPrice() {
        return this.price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @SerializedName("Quantity")
    private long quantity;

    public long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @SerializedName("SkuAttr")
    private AddOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem[] skuAttr;

    public AddOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem[] getSkuAttr() {
        return this.skuAttr;
    }

    public void setSkuAttr(AddOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem[] skuAttr) {
        this.skuAttr = skuAttr;
    }
}
