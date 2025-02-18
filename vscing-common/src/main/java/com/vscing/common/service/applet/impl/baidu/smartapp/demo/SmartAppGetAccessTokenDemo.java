// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.GetAccessTokenException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.GetAccessTokenRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppGetAccessToken;

public class SmartAppGetAccessTokenDemo {

    public static void main(String[] args) {
        GetAccessTokenRequest param = new GetAccessTokenRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：grant_type，实际使用时请替换成真实参数
        param.setGrantType("client_credentials");
        // 文档中对应字段：client_id，实际使用时请替换成真实参数
        param.setClientID("5fecoAqgCIUtzIyA6FAPgoyrc6oUc55c");
        // 文档中对应字段：client_secret，实际使用时请替换成真实参数
        param.setClientSecret("rZ7BcWQPSQxS2jhCwSVVx1gfRdNWn7TO");
        // 文档中对应字段：scope，实际使用时请替换成真实参数
        param.setScope("smartapp_snsapi_base");

        try {
            System.out.println(new Gson().toJson(SmartAppGetAccessToken.getAccessToken(param)));
        } catch (GetAccessTokenException e) {
            e.printStackTrace();
        }
    }
}
