// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.ModifyMaterialException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.ModifyMaterialRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppModifyMaterial;

public class SmartAppModifyMaterialDemo {

    public static void main(String[] args) {
        ModifyMaterialRequest param = new ModifyMaterialRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("60.3fa044ebfa2c631dda1b7a133c67357e.1776534.2801703505.884408-62860723");
        param.setAppID(06173460L); // 文档中对应字段：app_id，实际使用时请替换成真实参数
        param.setID(36857L); // 文档中对应字段：id，实际使用时请替换成真实参数
        param.setImageURL(
            "https://mbs0.bdstatic.com/searchbox/mappconsole/image/00832078/d41d0635-abf0-2d80-a2c0-df2e2b12604b.png"); // 文档中对应字段：imageUrl，实际使用时请替换成真实参数
        param.setTitle("修改测试数据"); // 文档中对应字段：title，实际使用时请替换成真实参数
        param.setPath("/pages/index/index"); // 文档中对应字段：path，实际使用时请替换成真实参数
        param.setCategory1Code("5"); // 文档中对应字段：category1Code，实际使用时请替换成真实参数
        param.setCategory2Code("55651"); // 文档中对应字段：category2Code，实际使用时请替换成真实参数
        param.setDesc("xxxx"); // 文档中对应字段：desc，实际使用时请替换成真实参数
        param.setLabelAttr("xxxx"); // 文档中对应字段：labelAttr，实际使用时请替换成真实参数
        param.setLabelDiscount("xxxx"); // 文档中对应字段：labelDiscount，实际使用时请替换成真实参数
        param.setButtonName("xxxx"); // 文档中对应字段：buttonName，实际使用时请替换成真实参数
        param.setBigImage("xxxx"); // 文档中对应字段：bigImage，实际使用时请替换成真实参数
        param.setVerticalImage("xxxx"); // 文档中对应字段：verticalImage，实际使用时请替换成真实参数
        param.setExtJSON("xxxx"); // 文档中对应字段：extJson，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppModifyMaterial.modifyMaterial(param)));
        } catch (ModifyMaterialException e) {
            e.printStackTrace();
        }
    }
}
