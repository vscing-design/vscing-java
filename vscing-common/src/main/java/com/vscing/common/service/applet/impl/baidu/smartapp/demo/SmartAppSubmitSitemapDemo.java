// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppSubmitSitemap;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitSitemapException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitSitemapRequest;

public class SmartAppSubmitSitemapDemo {

    public static void main(String[] args) {
        SubmitSitemapRequest param = new SubmitSitemapRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("50.3fa686ebfa4c108dda3b1a756c32772e.5126454.3855663268.545533-15583210");
        param.setAppID(82552681L); // 文档中对应字段：app_id，实际使用时请替换成真实参数
        param.setDesc("智能小程序示例"); // 文档中对应字段：desc，实际使用时请替换成真实参数
        param.setFrequency("5"); // 文档中对应字段：frequency，实际使用时请替换成真实参数
        param.setType("6"); // 文档中对应字段：type，实际使用时请替换成真实参数
        param.setURL(
            "https://mbs1.bdstatic.com/searchbox/mappconsole/image/17443841/375c82eb-e321-6b01-28ed-0f316a5e0d62.json"); // 文档中对应字段：url，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppSubmitSitemap.submitSitemap(param)));
        } catch (SubmitSitemapException e) {
            e.printStackTrace();
        }
    }
}
