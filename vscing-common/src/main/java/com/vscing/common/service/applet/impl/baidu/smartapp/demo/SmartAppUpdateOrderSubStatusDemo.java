// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppUpdateOrderSubStatus;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderSubStatusException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderSubStatusRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderSubStatusRequestDataItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderSubStatusRequestDataItemEXT;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderSubStatusRequestDataItemEXTSubsOrder;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderSubStatusRequestDataItemEXTSubsOrderItemsItem;

public class SmartAppUpdateOrderSubStatusDemo {

    public static void main(String[] args) {
        UpdateOrderSubStatusRequest param = new UpdateOrderSubStatusRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("72.522f47742a6241a2c5ad5c038237eb73.3280875.3382286506.341125-24133836");
        // 文档中对应字段：open_id，实际使用时请替换成真实参数
        param.setOpenID("k24HEREQhWhWWB0WYqYT2ITUGX");
        // 文档中对应字段：scene_id，实际使用时请替换成真实参数
        param.setSceneID("4375456875467");
        // 文档中对应字段：scene_type，实际使用时请替换成真实参数
        param.setSceneType(7);
        // 文档中对应字段：pm_app_key，实际使用时请替换成真实参数
        param.setPmAppKey("WXF8pGOvo0TTGU3qCMMhEjvFBkF0bO1Z");
        UpdateOrderSubStatusRequestDataItemEXTSubsOrderItemsItem
            updateOrderSubStatusRequestDataItemEXTSubsOrderItemsItem =
                new UpdateOrderSubStatusRequestDataItemEXTSubsOrderItemsItem();
        updateOrderSubStatusRequestDataItemEXTSubsOrderItemsItem.setSubOrderID(
            "onlyOne"); // 文档中对应字段：SubOrderID，实际使用时请替换成真实参数
        updateOrderSubStatusRequestDataItemEXTSubsOrderItemsItem.setSubStatus(403L); // 文档中对应字段：SubStatus，实际使用时请替换成真实参数

        UpdateOrderSubStatusRequestDataItemEXTSubsOrder updateOrderSubStatusRequestDataItemEXTSubsOrder =
            new UpdateOrderSubStatusRequestDataItemEXTSubsOrder();
        updateOrderSubStatusRequestDataItemEXTSubsOrder.setItems(
            new UpdateOrderSubStatusRequestDataItemEXTSubsOrderItemsItem[] {
                updateOrderSubStatusRequestDataItemEXTSubsOrderItemsItem
            }); // 文档中对应字段：Items，实际使用时请替换成真实参数

        updateOrderSubStatusRequestDataItemEXTSubsOrder.setStatus(403L); // 文档中对应字段：Status，实际使用时请替换成真实参数

        UpdateOrderSubStatusRequestDataItemEXT updateOrderSubStatusRequestDataItemEXT =
            new UpdateOrderSubStatusRequestDataItemEXT();
        updateOrderSubStatusRequestDataItemEXT.setSubsOrder(
            updateOrderSubStatusRequestDataItemEXTSubsOrder); // 文档中对应字段：SubsOrder，实际使用时请替换成真实参数

        UpdateOrderSubStatusRequestDataItem updateOrderSubStatusRequestDataItem =
            new UpdateOrderSubStatusRequestDataItem();
        updateOrderSubStatusRequestDataItem.setBizAPPID(
            "WXF5pGOvo4TTGU3qCMMhEjvFBkF1bO3Z"); // 文档中对应字段：BizAPPID，实际使用时请替换成真实参数
        updateOrderSubStatusRequestDataItem.setCateID(1L); // 文档中对应字段：CateID，实际使用时请替换成真实参数
        updateOrderSubStatusRequestDataItem.setEXT(updateOrderSubStatusRequestDataItemEXT); // 文档中对应字段：EXT，实际使用时请替换成真实参数
        updateOrderSubStatusRequestDataItem.setResourceID("7802141008655"); // 文档中对应字段：ResourceID，实际使用时请替换成真实参数

        param.setData(
            new UpdateOrderSubStatusRequestDataItem[] {
                updateOrderSubStatusRequestDataItem
            }); // 文档中对应字段：Data，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppUpdateOrderSubStatus.updateOrderSubStatus(param)));
        } catch (UpdateOrderSubStatusException e) {
            e.printStackTrace();
        }
    }
}
