// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.ConsumeCouponCodeException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.ConsumeCouponCodeRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppConsumeCouponCode;

public class SmartAppConsumeCouponCodeDemo {

    public static void main(String[] args) {
        ConsumeCouponCodeRequest param = new ConsumeCouponCodeRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("82.375f72145a6613a7c7ad1c250010eb14.3813823.1167855387.641388-26064206");
        param.setCouponID("4152336676"); // 文档中对应字段：couponId，实际使用时请替换成真实参数
        param.setOpenID("k80HEREQhWhWWB7WYqYT0ITUGX"); // 文档中对应字段：openId，实际使用时请替换成真实参数
        param.setCouponTakeID(6475328776L); // 文档中对应字段：couponTakeId，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppConsumeCouponCode.consumeCouponCode(param)));
        } catch (ConsumeCouponCodeException e) {
            e.printStackTrace();
        }
    }
}
