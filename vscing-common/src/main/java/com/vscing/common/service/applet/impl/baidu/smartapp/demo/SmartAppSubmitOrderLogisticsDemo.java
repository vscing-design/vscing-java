// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppSubmitOrderLogistics;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitOrderLogisticsException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitOrderLogisticsRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitOrderLogisticsRequestDataItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitOrderLogisticsRequestDataItemEXT;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitOrderLogisticsRequestDataItemEXTMainOrder;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitOrderLogisticsRequestDataItemEXTMainOrderExpress;

public class SmartAppSubmitOrderLogisticsDemo {

    public static void main(String[] args) {
        SubmitOrderLogisticsRequest param = new SubmitOrderLogisticsRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("18.285f07664a0047a2c2ad4c680402eb72.1332507.2562518880.208804-77076471");
        // 文档中对应字段：open_id，实际使用时请替换成真实参数
        param.setOpenID("k82HEREQhWhWWB8WYqYT8ITUGX");
        // 文档中对应字段：scene_id，实际使用时请替换成真实参数
        param.setSceneID("8722255006805");
        // 文档中对应字段：scene_type，实际使用时请替换成真实参数
        param.setSceneType(3);
        // 文档中对应字段：pm_app_key，实际使用时请替换成真实参数
        param.setPmAppKey("WXF0pGOvo3TTGU1qCMMhEjvFBkF1bO8Z");
        SubmitOrderLogisticsRequestDataItemEXTMainOrderExpress submitOrderLogisticsRequestDataItemEXTMainOrderExpress =
            new SubmitOrderLogisticsRequestDataItemEXTMainOrderExpress();
        submitOrderLogisticsRequestDataItemEXTMainOrderExpress.setCode("SFEXPRESS"); // 文档中对应字段：Code，实际使用时请替换成真实参数
        submitOrderLogisticsRequestDataItemEXTMainOrderExpress.setID("2703423827"); // 文档中对应字段：ID，实际使用时请替换成真实参数
        submitOrderLogisticsRequestDataItemEXTMainOrderExpress.setName("顺丰快递"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        submitOrderLogisticsRequestDataItemEXTMainOrderExpress.setStatus(0L); // 文档中对应字段：Status，实际使用时请替换成真实参数
        submitOrderLogisticsRequestDataItemEXTMainOrderExpress.setType(1L); // 文档中对应字段：Type，实际使用时请替换成真实参数

        SubmitOrderLogisticsRequestDataItemEXTMainOrder submitOrderLogisticsRequestDataItemEXTMainOrder =
            new SubmitOrderLogisticsRequestDataItemEXTMainOrder();
        submitOrderLogisticsRequestDataItemEXTMainOrder.setExpress(
            submitOrderLogisticsRequestDataItemEXTMainOrderExpress); // 文档中对应字段：Express，实际使用时请替换成真实参数

        SubmitOrderLogisticsRequestDataItemEXT submitOrderLogisticsRequestDataItemEXT =
            new SubmitOrderLogisticsRequestDataItemEXT();
        submitOrderLogisticsRequestDataItemEXT.setMainOrder(
            submitOrderLogisticsRequestDataItemEXTMainOrder); // 文档中对应字段：MainOrder，实际使用时请替换成真实参数

        SubmitOrderLogisticsRequestDataItem submitOrderLogisticsRequestDataItem =
            new SubmitOrderLogisticsRequestDataItem();
        submitOrderLogisticsRequestDataItem.setBizAPPID(
            "WXF1pGOvo6TTGU2qCMMhEjvFBkF3bO0Z"); // 文档中对应字段：BizAPPID，实际使用时请替换成真实参数
        submitOrderLogisticsRequestDataItem.setCateID(2L); // 文档中对应字段：CateID，实际使用时请替换成真实参数
        submitOrderLogisticsRequestDataItem.setEXT(submitOrderLogisticsRequestDataItemEXT); // 文档中对应字段：EXT，实际使用时请替换成真实参数
        submitOrderLogisticsRequestDataItem.setResourceID("0815535302548"); // 文档中对应字段：ResourceID，实际使用时请替换成真实参数

        param.setData(
            new SubmitOrderLogisticsRequestDataItem[] {
                submitOrderLogisticsRequestDataItem
            }); // 文档中对应字段：Data，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppSubmitOrderLogistics.submitOrderLogistics(param)));
        } catch (SubmitOrderLogisticsException e) {
            e.printStackTrace();
        }
    }
}
