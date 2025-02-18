// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.DeleteCouponBannerException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.DeleteCouponBannerRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppDeleteCouponBanner;

public class SmartAppDeleteCouponBannerDemo {

    public static void main(String[] args) {
        DeleteCouponBannerRequest param = new DeleteCouponBannerRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("64.656f80654a8110a0c1ad2c344586eb28.3314323.0783884671.135218-06445074");
        param.setCouponID("8216485338"); // 文档中对应字段：couponId，实际使用时请替换成真实参数
        param.setBannerID(3541671780L); // 文档中对应字段：bannerId，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppDeleteCouponBanner.deleteCouponBanner(param)));
        } catch (DeleteCouponBannerException e) {
            e.printStackTrace();
        }
    }
}
