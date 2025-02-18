// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.CheckPathException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.CheckPathRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppCheckPath;

public class SmartAppCheckPathDemo {

    public static void main(String[] args) {
        CheckPathRequest param = new CheckPathRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("57.667f16355a7115a7c1ad7c268166eb60.7705383.6785724621.134074-42870685");
        param.setPath("/pages/article/article?id=37077&amp;type=1"); // 文档中对应字段：path，实际使用时请替换成真实参数
        param.setType(new String[] {"risk", "porn", "ocr-word", "ocr-lead"}); // 文档中对应字段：type，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppCheckPath.checkPath(param)));
        } catch (CheckPathException e) {
            e.printStackTrace();
        }
    }
}
