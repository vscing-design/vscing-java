// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.GetCommentListException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.GetCommentListRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppGetCommentList;

public class SmartAppGetCommentListDemo {

    public static void main(String[] args) {
        GetCommentListRequest param = new GetCommentListRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("43.7e236302ce418eb2bf04c847af30ac27.5061853.6554548746.633057-74705463");
        // 文档中对应字段：host_name，实际使用时请替换成真实参数
        param.setHostName("baiduboxapp");
        param.setSnid("ts_article_c520ed4e52ce02886f4646eb"); // 文档中对应字段：snid，实际使用时请替换成真实参数
        param.setSnidType("xxxx"); // 文档中对应字段：snid_type，实际使用时请替换成真实参数
        param.setStart(5L); // 文档中对应字段：start，实际使用时请替换成真实参数
        param.setNum(5L); // 文档中对应字段：num，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppGetCommentList.getCommentList(param)));
        } catch (GetCommentListException e) {
            e.printStackTrace();
        }
    }
}
