// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppSubmitSkuCoupon;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitSkuCouponException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitSkuCouponRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitSkuCouponRequestPostBodyItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SubmitSkuCouponRequestPostBodyItemPriceInfo;

public class SmartAppSubmitSkuCouponDemo {

    public static void main(String[] args) {
        SubmitSkuCouponRequest param = new SubmitSkuCouponRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        param.setAccessToken("xxx");

        SubmitSkuCouponRequestPostBodyItemPriceInfo submitSkuCouponRequestPostBodyItemPriceInfo =
            new SubmitSkuCouponRequestPostBodyItemPriceInfo();
        submitSkuCouponRequestPostBodyItemPriceInfo.setOrgPrice("0");
        submitSkuCouponRequestPostBodyItemPriceInfo.setRealPrice("0");

        SubmitSkuCouponRequestPostBodyItem submitSkuCouponRequestPostBodyItem =
            new SubmitSkuCouponRequestPostBodyItem();
        submitSkuCouponRequestPostBodyItem.setPriceInfo(submitSkuCouponRequestPostBodyItemPriceInfo);

        submitSkuCouponRequestPostBodyItem.setTradeType(0);
        submitSkuCouponRequestPostBodyItem.setDesc("xxx");
        submitSkuCouponRequestPostBodyItem.setImages(new String[] {"xxx"});

        submitSkuCouponRequestPostBodyItem.setSchema("xxx");
        submitSkuCouponRequestPostBodyItem.setPath("xxx");
        submitSkuCouponRequestPostBodyItem.setTitle("xxx");
        submitSkuCouponRequestPostBodyItem.setTag("xxx");
        submitSkuCouponRequestPostBodyItem.setRegion("xxx");
        submitSkuCouponRequestPostBodyItem.setTradeType(0);
        param.setSubmitSkuCouponRequestPostBodyItem(
            new SubmitSkuCouponRequestPostBodyItem[] {submitSkuCouponRequestPostBodyItem});

        try {
            System.out.println(new Gson().toJson(SmartAppSubmitSkuCoupon.submitSkuCoupon(param)));
        } catch (SubmitSkuCouponException e) {
            e.printStackTrace();
        }
    }
}
