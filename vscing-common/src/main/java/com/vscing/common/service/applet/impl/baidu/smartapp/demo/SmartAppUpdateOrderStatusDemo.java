// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppUpdateOrderStatus;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderStatusException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderStatusRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderStatusRequestDataItem;

public class SmartAppUpdateOrderStatusDemo {

    public static void main(String[] args) {
        UpdateOrderStatusRequest param = new UpdateOrderStatusRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("72.078f20132a3175a0c3ad0c047604eb13.8584863.3716801581.207845-38735613");
        // 文档中对应字段：open_id，实际使用时请替换成真实参数
        param.setOpenID("k53HEREQhWhWWB7WYqYT4ITUGX");
        // 文档中对应字段：scene_id，实际使用时请替换成真实参数
        param.setSceneID("7301630575534");
        // 文档中对应字段：scene_type，实际使用时请替换成真实参数
        param.setSceneType(2);
        // 文档中对应字段：pm_app_key，实际使用时请替换成真实参数
        param.setPmAppKey("WXF2pGOvo6TTGU3qCMMhEjvFBkF0bO5Z");
        UpdateOrderStatusRequestDataItem updateOrderStatusRequestDataItem = new UpdateOrderStatusRequestDataItem();
        updateOrderStatusRequestDataItem.setBizAPPID(
            "WXF2pGOvo6TTGU2qCMMhEjvFBkF6bO6Z"); // 文档中对应字段：BizAPPID，实际使用时请替换成真实参数
        updateOrderStatusRequestDataItem.setCateID(5L); // 文档中对应字段：CateID，实际使用时请替换成真实参数
        updateOrderStatusRequestDataItem.setResourceID(
            "G00554626312227151135661103658"); // 文档中对应字段：ResourceID，实际使用时请替换成真实参数
        updateOrderStatusRequestDataItem.setStatus(1L); // 文档中对应字段：Status，实际使用时请替换成真实参数

        param.setData(
            new UpdateOrderStatusRequestDataItem[] {updateOrderStatusRequestDataItem}); // 文档中对应字段：Data，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppUpdateOrderStatus.updateOrderStatus(param)));
        } catch (UpdateOrderStatusException e) {
            e.printStackTrace();
        }
    }
}
