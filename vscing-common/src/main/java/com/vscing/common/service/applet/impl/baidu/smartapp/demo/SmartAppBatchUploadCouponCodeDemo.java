// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.BatchUploadCouponCodeException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.BatchUploadCouponCodeRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppBatchUploadCouponCode;

public class SmartAppBatchUploadCouponCodeDemo {

    public static void main(String[] args) {
        BatchUploadCouponCodeRequest param = new BatchUploadCouponCodeRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("46.561f63787a1050a6c4ad1c486306eb86.0001520.5608344134.473202-25353430");
        param.setCouponID("1053873876"); // 文档中对应字段：couponId，实际使用时请替换成真实参数
        param.setCouponCodes("4511270525"); // 文档中对应字段：couponCodes，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppBatchUploadCouponCode.batchUploadCouponCode(param)));
        } catch (BatchUploadCouponCodeException e) {
            e.printStackTrace();
        }
    }
}
