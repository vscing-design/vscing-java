// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddMaterialException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddMaterialRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppAddMaterial;

public class SmartAppAddMaterialDemo {

    public static void main(String[] args) {
        AddMaterialRequest param = new AddMaterialRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("08.6fa050ebfa1c838dda1b5a768c21748e.3205233.7587155332.154056-04287857");
        param.setAppID(55561425L); // 文档中对应字段：app_id，实际使用时请替换成真实参数
        param.setImageURL(
            "https://mbs4.bdstatic.com/searchbox/mappconsole/image/45350556/d21d3876-abf3-6d50-a6c4-df6e0b57334b.png"); // 文档中对应字段：imageUrl，实际使用时请替换成真实参数
        param.setTitle("测试数据"); // 文档中对应字段：title，实际使用时请替换成真实参数
        param.setPath("/pages/index/index"); // 文档中对应字段：path，实际使用时请替换成真实参数
        param.setCategory1Code("3"); // 文档中对应字段：category1Code，实际使用时请替换成真实参数
        param.setCategory2Code("57207"); // 文档中对应字段：category2Code，实际使用时请替换成真实参数
        param.setDesc("xxxx"); // 文档中对应字段：desc，实际使用时请替换成真实参数
        param.setLabelAttr("xxxx"); // 文档中对应字段：labelAttr，实际使用时请替换成真实参数
        param.setLabelDiscount("xxxx"); // 文档中对应字段：labelDiscount，实际使用时请替换成真实参数
        param.setButtonName("xxxx"); // 文档中对应字段：buttonName，实际使用时请替换成真实参数
        param.setBigImage("xxxx"); // 文档中对应字段：bigImage，实际使用时请替换成真实参数
        param.setVerticalImage("xxxx"); // 文档中对应字段：verticalImage，实际使用时请替换成真实参数
        param.setExtJSON("xxxx"); // 文档中对应字段：extJson，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppAddMaterial.addMaterial(param)));
        } catch (AddMaterialException e) {
            e.printStackTrace();
        }
    }
}
