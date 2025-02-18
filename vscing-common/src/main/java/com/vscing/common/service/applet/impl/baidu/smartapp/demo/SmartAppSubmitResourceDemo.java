// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppSubmitResource;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitResourceException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitResourceRequest;

public class SmartAppSubmitResourceDemo {

    public static void main(String[] args) {
        SubmitResourceRequest param = new SubmitResourceRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("04.5fa810ebfa8c587dda3b1a681c10858e.7415871.6827460565.671382-65170832");
        param.setAppID(45400413L); // 文档中对应字段：app_id，实际使用时请替换成真实参数
        param.setBody("黄油化开备用，黄油化开后加入糖霜，搅拌均匀，加入蛋清，继续打匀，加入切碎的蔓越莓，继续搅拌。蔓越莓放多少根据自己的喜..."); // 文档中对应字段：body，实际使用时请替换成真实参数
        param.setExt("{\"publish_time\": \"2021年 11 月 1 日\"}"); // 文档中对应字段：ext，实际使用时请替换成真实参数
        param.setFeedSubType("明星八卦（可选有限集合）"); // 文档中对应字段：feed_sub_type，实际使用时请替换成真实参数
        param.setFeedType("娱乐（可选有限集合）"); // 文档中对应字段：feed_type，实际使用时请替换成真实参数
        param.setImages("[\"https://z5.ax3x.com/6608/60/20/IP7kw2.jpg\"]"); // 文档中对应字段：images，实际使用时请替换成真实参数
        param.setMappSubType("7353"); // 文档中对应字段：mapp_sub_type，实际使用时请替换成真实参数
        param.setMappType("2368"); // 文档中对应字段：mapp_type，实际使用时请替换成真实参数
        param.setPath("/pages/detail/detail?id=571544"); // 文档中对应字段：path，实际使用时请替换成真实参数
        param.setTags("电影"); // 文档中对应字段：tags，实际使用时请替换成真实参数
        param.setTitle("百度智能小程序，给你全新的智能体验"); // 文档中对应字段：title，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppSubmitResource.submitResource(param)));
        } catch (SubmitResourceException e) {
            e.printStackTrace();
        }
    }
}
