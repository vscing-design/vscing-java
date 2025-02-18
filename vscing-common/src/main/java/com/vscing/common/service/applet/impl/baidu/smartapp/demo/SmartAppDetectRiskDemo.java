// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.DetectRiskException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.DetectRiskRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppDetectRisk;

public class SmartAppDetectRiskDemo {

    public static void main(String[] args) {
        DetectRiskRequest param = new DetectRiskRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("58.007f76425a7635a5c0ad3c033222eb12.3201818.8170412601.046706-53683775");
        param.setAppkey("WXF4pGOvo4TTGU7qCMMhEjvFBkF4bO4Z"); // 文档中对应字段：appkey，实际使用时请替换成真实参数
        // 文档中对应字段：xtoken，实际使用时请替换成真实参数
        param.setXtoken(
            "{\"key\":\"jU+mx8VJ5+k1+JfN2cLPNfQZIVbCAZwhMIlTtn"
                + "t7yl+YTPK0E+81s46UwTqR0eKQEyKu5Qbd2xknxNNoYl4w5o/8/qemfUNn2pDXmsYn"
                + "aZz3tM7k1bhRD7TusfOXXqXRo6gWuUdnWttnIhxvYKGwhzL7sUF7fqnxY2S7PUnGE8g=\", \"va"
                + "lue\": \"TPHtjm6RTDX2pUpcUjbhRu/t5MA33kF+mFv5DPmNSx1zMsTsT0Yitu+DoQ7CJS7f7tQBH"
                + "pqzQ8vfW0nV0Zm6HWkkXK1xkF4jSTSEWH4KkLAMdzWwqLKZQTaWG5r+MU+0qOqYF1mc25oB0WSSfPJQ"
                + "3ZUYpY+7RezUMWK8xyUB/5vEy54HZ7SYZjsfmJOYNcVsh8A0fTsoHDsNBiXYA5KUe3ZxiSzmyLYe2EYj"
                + "W8XLcL+iUgcToNuH267Ypn+Py4OxOD0lS0BgWVNV1sdGriYuRDAN7rcugPbVscFoEeOcDWIDaHNKs740vD"
                + "vmQQCc6M8EXsQ8W/NDdze75dgJ3AL3ZLV+7Ahe6ISoxflpRKjvl6Jl53+p3jESon2DLJA45/+n8FAbCifa0"
                + "mZLvyHJ+gTSR5h3lLSZW5ZntrbeofVP3MZTYsPip2k3Kt1A3G/ABj0K6k8FIx4iM8UQWvPgFFOJ/vbCf1c"
                + "4FXVDLHDid1V0qGwJ5TTRur8MJH7yVPiS6dltOQkIIAQcK8C+nTgi+EKY8RwwoOYw\"}");
        param.setType("marketing"); // 文档中对应字段：type，实际使用时请替换成真实参数
        param.setClientip("745.81.83.065"); // 文档中对应字段：clientip，实际使用时请替换成真实参数
        param.setTs(47567108L); // 文档中对应字段：ts，实际使用时请替换成真实参数
        param.setEv("0"); // 文档中对应字段：ev，实际使用时请替换成真实参数
        // 文档中对应字段：useragent，实际使用时请替换成真实参数
        param.setUseragent(
            "Mozilla/3.6 (Macintosh, Intel Mac OS X 31_30_6) AppleWebKi"
                + "t/348.80 (KHTML, like Gecko) Chrome/85.6.7467.61 Safari/721.60");
        param.setPhone("xxxx"); // 文档中对应字段：phone，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppDetectRisk.detectRisk(param)));
        } catch (DetectRiskException e) {
            e.printStackTrace();
        }
    }
}
