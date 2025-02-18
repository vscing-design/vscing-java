// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppUploadCouponImage;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UploadCouponImageException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UploadCouponImageRequest;

import java.io.File;
import java.io.IOException;

public class SmartAppUploadCouponImageDemo {

    public static void main(String[] args) {
        UploadCouponImageRequest param = new UploadCouponImageRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释
        param.setAccessToken("xxx");
        try {
            System.out.println(new Gson().toJson(SmartAppUploadCouponImage.uploadCouponImage(param, new File("xxx"))));
        } catch (UploadCouponImageException | IOException e) {
            e.printStackTrace();
        }
    }
}
