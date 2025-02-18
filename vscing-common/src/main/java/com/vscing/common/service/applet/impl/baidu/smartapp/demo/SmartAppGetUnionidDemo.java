// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.GetUnionidException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.GetUnionidRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppGetUnionid;

public class SmartAppGetUnionidDemo {

    public static void main(String[] args) {
        GetUnionidRequest param = new GetUnionidRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("17.270f06413a6663a2c8ad7c452418eb48.0144087.7822622322.762162-66065544");
        param.setOpenid("k76HEREQhWhWWB0WYqYT1ITUGX"); // 文档中对应字段：openid，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppGetUnionid.getUnionid(param)));
        } catch (GetUnionidException e) {
            e.printStackTrace();
        }
    }
}
