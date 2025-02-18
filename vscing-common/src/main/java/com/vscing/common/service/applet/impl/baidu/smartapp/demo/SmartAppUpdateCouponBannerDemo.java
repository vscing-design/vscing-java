// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppUpdateCouponBanner;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateCouponBannerException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateCouponBannerRequest;

public class SmartAppUpdateCouponBannerDemo {

    public static void main(String[] args) {
        UpdateCouponBannerRequest param = new UpdateCouponBannerRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("64.843f41044a4026a1c4ad8c368600eb50.5754130.2312710106.676400-02138528");
        param.setCouponID("0642104148"); // 文档中对应字段：couponId，实际使用时请替换成真实参数
        param.setPicURL(
            "https://mbs2.bdstatic.com/searchbox/mappconsole/image/51815637/3f2c0a0b-fe5e-3367-b22b-4a3f38e21f46.png"); // 文档中对应字段：picUrl，实际使用时请替换成真实参数
        param.setTitle("修改banner"); // 文档中对应字段：title，实际使用时请替换成真实参数
        param.setAppRedirectPath("/test"); // 文档中对应字段：appRedirectPath，实际使用时请替换成真实参数
        param.setBannerID(80L); // 文档中对应字段：bannerId，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppUpdateCouponBanner.updateCouponBanner(param)));
        } catch (UpdateCouponBannerException e) {
            e.printStackTrace();
        }
    }
}
