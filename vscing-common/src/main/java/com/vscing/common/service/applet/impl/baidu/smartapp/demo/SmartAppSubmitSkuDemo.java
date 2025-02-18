// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppSubmitSku;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitSkuException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitSkuRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitSkuRequestPostBodyItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitSkuRequestPostBodyItemActivityInfoItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitSkuRequestPostBodyItemPriceInfo;

public class SmartAppSubmitSkuDemo {

    public static void main(String[] args) {
        SubmitSkuRequest param = new SubmitSkuRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        param.setAccessToken("xxx");
        SubmitSkuRequestPostBodyItemActivityInfoItem submitSkuRequestPostBodyItemActivityInfoItem =
            new SubmitSkuRequestPostBodyItemActivityInfoItem();
        submitSkuRequestPostBodyItemActivityInfoItem.setActivityDesc("xxx");
        submitSkuRequestPostBodyItemActivityInfoItem.setActivityStartTime(0);
        submitSkuRequestPostBodyItemActivityInfoItem.setActivityEndTime(0);
        submitSkuRequestPostBodyItemActivityInfoItem.setActivityPath("xxxx");
        submitSkuRequestPostBodyItemActivityInfoItem.setActivityType("xxx");

        SubmitSkuRequestPostBodyItemPriceInfo submitSkuRequestPostBodyItemPriceInfo =
            new SubmitSkuRequestPostBodyItemPriceInfo();
        submitSkuRequestPostBodyItemPriceInfo.setOrgPrice("xxx");
        submitSkuRequestPostBodyItemPriceInfo.setOrgUnit("xxx");
        submitSkuRequestPostBodyItemPriceInfo.setRealPrice("xxx");

        SubmitSkuRequestPostBodyItem submitSkuRequestPostBodyItem = new SubmitSkuRequestPostBodyItem();
        submitSkuRequestPostBodyItem.setActivityInfo(
            new SubmitSkuRequestPostBodyItemActivityInfoItem[] {submitSkuRequestPostBodyItemActivityInfoItem});
        submitSkuRequestPostBodyItem.setPriceInfo(submitSkuRequestPostBodyItemPriceInfo);

        submitSkuRequestPostBodyItem.setTradeType(0);
        submitSkuRequestPostBodyItem.setDesc("xxx");
        submitSkuRequestPostBodyItem.setImages(new String[] {"xxx"});
        submitSkuRequestPostBodyItem.setButtonName("xxx");
        submitSkuRequestPostBodyItem.setSchema("xxx");
        submitSkuRequestPostBodyItem.setPath("xxx");
        submitSkuRequestPostBodyItem.setTitle("xxx");
        submitSkuRequestPostBodyItem.setTag("xxx");
        submitSkuRequestPostBodyItem.setRegion("xxx");
        param.setSubmitSkuRequestPostBodyItem(new SubmitSkuRequestPostBodyItem[] {submitSkuRequestPostBodyItem});

        try {
            System.out.println(new Gson().toJson(SmartAppSubmitSku.submitSku(param)));
        } catch (SubmitSkuException e) {
            e.printStackTrace();
        }
    }
}
