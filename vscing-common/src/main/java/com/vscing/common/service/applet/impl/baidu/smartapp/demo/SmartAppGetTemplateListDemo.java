// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.GetTemplateListException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.GetTemplateListRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppGetTemplateList;

public class SmartAppGetTemplateListDemo {

    public static void main(String[] args) {
        GetTemplateListRequest param = new GetTemplateListRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("81.1fa387ebfa5c417dda6b6a151c70807e.5864535.8257756548.306728-32180373");
        // 文档中对应字段：offset，实际使用时请替换成真实参数
        param.setOffset(0);
        // 文档中对应字段：count，实际使用时请替换成真实参数
        param.setCount(7);

        try {
            System.out.println(new Gson().toJson(SmartAppGetTemplateList.getTemplateList(param)));
        } catch (GetTemplateListException e) {
            e.printStackTrace();
        }
    }
}
