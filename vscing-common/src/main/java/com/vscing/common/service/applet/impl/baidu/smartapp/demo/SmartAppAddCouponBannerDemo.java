// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddCouponBannerException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddCouponBannerRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppAddCouponBanner;

public class SmartAppAddCouponBannerDemo {

    public static void main(String[] args) {
        AddCouponBannerRequest param = new AddCouponBannerRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("00.565f54460a4112a1c7ad2c765677eb77.6030147.4251672205.380614-38113718");
        param.setCouponID("1718071308"); // 文档中对应字段：couponId，实际使用时请替换成真实参数
        param.setPicURL("/index/index"); // 文档中对应字段：picUrl，实际使用时请替换成真实参数
        param.setTitle("卡券标题"); // 文档中对应字段：title，实际使用时请替换成真实参数
        param.setAppRedirectPath("/test"); // 文档中对应字段：appRedirectPath，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppAddCouponBanner.addCouponBanner(param)));
        } catch (AddCouponBannerException e) {
            e.printStackTrace();
        }
    }
}
