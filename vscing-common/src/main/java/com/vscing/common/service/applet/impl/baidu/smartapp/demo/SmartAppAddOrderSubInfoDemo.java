// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderSubInfoException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderSubInfoRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderSubInfoRequestDataItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderSubInfoRequestDataItemEXT;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderSubInfoRequestDataItemEXTSubsOrder;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppAddOrderSubInfo;

public class SmartAppAddOrderSubInfoDemo {

    public static void main(String[] args) {
        AddOrderSubInfoRequest param = new AddOrderSubInfoRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("70.212f00074a4402a1c7ad1c521741eb35.5551507.5248244872.344867-02653437");
        // 文档中对应字段：open_id，实际使用时请替换成真实参数
        param.setOpenID("k72HEREQhWhWWB3WYqYT7ITUGX");
        // 文档中对应字段：scene_id，实际使用时请替换成真实参数
        param.setSceneID("3660333367668");
        // 文档中对应字段：scene_type，实际使用时请替换成真实参数
        param.setSceneType(4);
        // 文档中对应字段：pm_app_key，实际使用时请替换成真实参数
        param.setPmAppKey("WXF3pGOvo1TTGU7qCMMhEjvFBkF3bO0Z");
        AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail
            addOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail =
                new AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail();
        addOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail.setName("xxxx"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        addOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail.setStatus("1"); // 文档中对应字段：Status，实际使用时请替换成真实参数
        addOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail.setSwanSchema(
            "baiduboxapp://swan/B7GF4AWvCSr38myIs86uqaoYz1pPCSY3/wjz/bdxd/order-detail/order-detail?orderId=150351856634"); // 文档中对应字段：SwanSchema，实际使用时请替换成真实参数

        AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem
            addOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem =
                new AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem();
        addOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem.setAmount(
            100L); // 文档中对应字段：Amount，实际使用时请替换成真实参数
        addOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem.setID(
            "717865582867"); // 文档中对应字段：ID，实际使用时请替换成真实参数
        addOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem.setQuantity(
            1L); // 文档中对应字段：Quantity，实际使用时请替换成真实参数

        AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund
            addOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund =
                new AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund();
        addOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund.setAmount("4845"); // 文档中对应字段：Amount，实际使用时请替换成真实参数
        addOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund.setProduct(
            new AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] {
                addOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem
            }); // 文档中对应字段：Product，实际使用时请替换成真实参数

        AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItem addOrderSubInfoRequestDataItemEXTSubsOrderItemsItem =
            new AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItem();
        addOrderSubInfoRequestDataItemEXTSubsOrderItemsItem.setCTime(1571026203L); // 文档中对应字段：CTime，实际使用时请替换成真实参数
        addOrderSubInfoRequestDataItemEXTSubsOrderItemsItem.setMTime(1571026203L); // 文档中对应字段：MTime，实际使用时请替换成真实参数
        addOrderSubInfoRequestDataItemEXTSubsOrderItemsItem.setOrderDetail(
            addOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail); // 文档中对应字段：OrderDetail，实际使用时请替换成真实参数
        addOrderSubInfoRequestDataItemEXTSubsOrderItemsItem.setOrderType(1L); // 文档中对应字段：OrderType，实际使用时请替换成真实参数
        addOrderSubInfoRequestDataItemEXTSubsOrderItemsItem.setRefund(
            addOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund); // 文档中对应字段：Refund，实际使用时请替换成真实参数
        addOrderSubInfoRequestDataItemEXTSubsOrderItemsItem.setSubOrderID(
            "onlyOne"); // 文档中对应字段：SubOrderID，实际使用时请替换成真实参数
        addOrderSubInfoRequestDataItemEXTSubsOrderItemsItem.setSubStatus(401L); // 文档中对应字段：SubStatus，实际使用时请替换成真实参数

        AddOrderSubInfoRequestDataItemEXTSubsOrder addOrderSubInfoRequestDataItemEXTSubsOrder =
            new AddOrderSubInfoRequestDataItemEXTSubsOrder();
        addOrderSubInfoRequestDataItemEXTSubsOrder.setItems(
            new AddOrderSubInfoRequestDataItemEXTSubsOrderItemsItem[] {
                addOrderSubInfoRequestDataItemEXTSubsOrderItemsItem
            }); // 文档中对应字段：Items，实际使用时请替换成真实参数

        addOrderSubInfoRequestDataItemEXTSubsOrder.setStatus(0); // 文档中对应字段：Status，实际使用时请替换成真实参数

        AddOrderSubInfoRequestDataItemEXT addOrderSubInfoRequestDataItemEXT = new AddOrderSubInfoRequestDataItemEXT();
        addOrderSubInfoRequestDataItemEXT.setSubsOrder(
            addOrderSubInfoRequestDataItemEXTSubsOrder); // 文档中对应字段：SubsOrder，实际使用时请替换成真实参数

        AddOrderSubInfoRequestDataItem addOrderSubInfoRequestDataItem = new AddOrderSubInfoRequestDataItem();
        addOrderSubInfoRequestDataItem.setBizAPPID(
            "WXF0pGOvo3TTGU7qCMMhEjvFBkF2bO8Z"); // 文档中对应字段：BizAPPID，实际使用时请替换成真实参数
        addOrderSubInfoRequestDataItem.setCateID(1L); // 文档中对应字段：CateID，实际使用时请替换成真实参数
        addOrderSubInfoRequestDataItem.setEXT(addOrderSubInfoRequestDataItemEXT); // 文档中对应字段：EXT，实际使用时请替换成真实参数
        addOrderSubInfoRequestDataItem.setResourceID("2153458565871"); // 文档中对应字段：ResourceID，实际使用时请替换成真实参数

        param.setData(
            new AddOrderSubInfoRequestDataItem[] {addOrderSubInfoRequestDataItem}); // 文档中对应字段：Data，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppAddOrderSubInfo.addOrderSubInfo(param)));
        } catch (AddOrderSubInfoException e) {
            e.printStackTrace();
        }
    }
}
