// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.GetSessionKeyException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.GetSessionKeyRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppGetSessionKey;

public class SmartAppGetSessionKeyDemo {

    public static void main(String[] args) {
        GetSessionKeyRequest param = new GetSessionKeyRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：code，实际使用时请替换成真实参数
        param.setCode("2b184bdb2c22525e24213d3a3ce8f185NW");
        // 文档中对应字段：client_id，实际使用时请替换成真实参数
        param.setClientID("4fecoAqgCIUtzIyA0FAPgoyrc0oUc33c");
        // 文档中对应字段：sk，实际使用时请替换成真实参数
        param.setSk("rZ4BcWQPSQxS7jhCwSVVx5gfRdNWn3TO");

        try {
            System.out.println(new Gson().toJson(SmartAppGetSessionKey.getSessionKey(param)));
        } catch (GetSessionKeyException e) {
            e.printStackTrace();
        }
    }
}
