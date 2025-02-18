// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppSubscribeSend;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubscribeSendException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubscribeSendRequest;

public class SmartAppSubscribeSendDemo {

    public static void main(String[] args) {
        SubscribeSendRequest param = new SubscribeSendRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("xxxx");
        param.setTemplateID("11360b364e7232a7b0df38db036a1fb8"); // 文档中对应字段：template_id，实际使用时请替换成真实参数
        param.setTouserOpenID("76GetTsw7nWRMVaYnlswLQ2t5y"); // 文档中对应字段：touser_openId，实际使用时请替换成真实参数
        param.setSubscribeID("xxxxx"); // 文档中对应字段：subscribe_id，实际使用时请替换成真实参数
        param.setData(
            "{\"keyword2\": {\"value\": \"2367-82-35\"},\"keyword3\": {\"value\": \"kfc\"}}"); // 文档中对应字段：data，实际使用时请替换成真实参数
        param.setPage("index?foo=bar"); // 文档中对应字段：page，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppSubscribeSend.subscribeSend(param)));
        } catch (SubscribeSendException e) {
            e.printStackTrace();
        }
    }
}
