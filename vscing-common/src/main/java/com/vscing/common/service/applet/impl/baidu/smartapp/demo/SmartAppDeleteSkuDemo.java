// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.DeleteSkuException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.DeleteSkuRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppDeleteSku;

public class SmartAppDeleteSkuDemo {

    public static void main(String[] args) {
        DeleteSkuRequest param = new DeleteSkuRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("03.bdc23bb4af1431e24b776f5807b71f83.8037834.7573635274.805033-234435324");
        param.setAppID(5433180L); // 文档中对应字段：app_id，实际使用时请替换成真实参数
        param.setPathList("[\"/pages/detail/detail?id=372413\"]"); // 文档中对应字段：path_list，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppDeleteSku.deleteSku(param)));
        } catch (DeleteSkuException e) {
            e.printStackTrace();
        }
    }
}
