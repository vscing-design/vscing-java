// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.GetSessionKeyV2Exception;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.GetSessionKeyV2Request;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppGetSessionKeyV2;

public class SmartAppGetSessionKeyV2Demo {

    public static void main(String[] args) {
        GetSessionKeyV2Request param = new GetSessionKeyV2Request();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：code，实际使用时请替换成真实参数
        param.setCode("0dc4c514b55223a1843263c75036db58NW");
        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("18.074f35548a7011a5c8ad2c163685eb01.6805152.8662454563.813862-53312003");
        // 文档中对应字段：client_id，实际使用时请替换成真实参数
        param.setClientID("WXF4pGOvo7TTGU7qCMMhEjvFBkF5bO7Z");
        // 文档中对应字段：sk，实际使用时请替换成真实参数
        param.setSk("xxxx");

        try {
            System.out.println(new Gson().toJson(SmartAppGetSessionKeyV2.getSessionKeyV2(param)));
        } catch (GetSessionKeyV2Exception e) {
            e.printStackTrace();
        }
    }
}
