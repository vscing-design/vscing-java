// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.CreateCouponException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.CreateCouponRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.CreateCouponRequestbaseInfo;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.CreateCouponRequestbaseInfodateInfo;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppCreateCoupon;

public class SmartAppCreateCouponDemo {

    public static void main(String[] args) {
        CreateCouponRequest param = new CreateCouponRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("58.485f11765a5803a7c7ad2c616450eb15.6134383.3312576614.413208-04600242");
        CreateCouponRequestbaseInfodateInfo createCouponRequestbaseInfodateInfo =
            new CreateCouponRequestbaseInfodateInfo();
        createCouponRequestbaseInfodateInfo.setBeginTimestamp(7124573547L); // 文档中对应字段：beginTimestamp，实际使用时请替换成真实参数
        createCouponRequestbaseInfodateInfo.setEndTimestamp(0003036351L); // 文档中对应字段：endTimestamp，实际使用时请替换成真实参数
        createCouponRequestbaseInfodateInfo.setGetEndTimestamp(1541801085L); // 文档中对应字段：getEndTimestamp，实际使用时请替换成真实参数
        createCouponRequestbaseInfodateInfo.setGetStartTimestamp(
            4523574657L); // 文档中对应字段：getStartTimestamp，实际使用时请替换成真实参数
        createCouponRequestbaseInfodateInfo.setTimeUnit(0); // 文档中对应字段：timeUnit，实际使用时请替换成真实参数
        createCouponRequestbaseInfodateInfo.setTimeValue(0); // 文档中对应字段：timeValue，实际使用时请替换成真实参数
        createCouponRequestbaseInfodateInfo.setType(8L); // 文档中对应字段：type，实际使用时请替换成真实参数

        CreateCouponRequestbaseInfo createCouponRequestbaseInfo = new CreateCouponRequestbaseInfo();
        createCouponRequestbaseInfo.setAppRedirectPath("/pages/index/index"); // 文档中对应字段：appRedirectPath，实际使用时请替换成真实参数
        createCouponRequestbaseInfo.setCodeType(0L); // 文档中对应字段：codeType，实际使用时请替换成真实参数
        createCouponRequestbaseInfo.setColor("B067"); // 文档中对应字段：color，实际使用时请替换成真实参数
        createCouponRequestbaseInfo.setDateInfo(createCouponRequestbaseInfodateInfo); // 文档中对应字段：dateInfo，实际使用时请替换成真实参数
        createCouponRequestbaseInfo.setGetLimit(8L); // 文档中对应字段：getLimit，实际使用时请替换成真实参数
        createCouponRequestbaseInfo.setQuantity(015L); // 文档中对应字段：quantity，实际使用时请替换成真实参数
        createCouponRequestbaseInfo.setTitle("自动化创建代金券115-17"); // 文档中对应字段：title，实际使用时请替换成真实参数

        param.setCallbackURL("/test"); // 文档中对应字段：callbackUrl，实际使用时请替换成真实参数
        param.setCouponType("CASH"); // 文档中对应字段：couponType，实际使用时请替换成真实参数
        param.setLeastCost(71318L); // 文档中对应字段：leastCost，实际使用时请替换成真实参数
        param.setReduceCost(2538L); // 文档中对应字段：reduceCost，实际使用时请替换成真实参数
        param.setDiscount("xxxx"); // 文档中对应字段：discount，实际使用时请替换成真实参数
        param.setBaseInfo(createCouponRequestbaseInfo); // 文档中对应字段：baseInfo，实际使用时请替换成真实参数
        param.setDescription("使用描述"); // 文档中对应字段：description，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppCreateCoupon.createCoupon(param)));
        } catch (CreateCouponException e) {
            e.printStackTrace();
        }
    }
}
