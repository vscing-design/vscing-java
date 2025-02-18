// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppUpdateOrderSubInfo;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderSubInfoException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderSubInfoRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderSubInfoRequestDataItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderSubInfoRequestDataItemEXT;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderSubInfoRequestDataItemEXTSubsOrder;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem;

public class SmartAppUpdateOrderSubInfoDemo {

    public static void main(String[] args) {
        UpdateOrderSubInfoRequest param = new UpdateOrderSubInfoRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("72.740f62661a1380a3c2ad6c660527eb20.7651347.8615308656.654148-72880712");
        // 文档中对应字段：open_id，实际使用时请替换成真实参数
        param.setOpenID("k07HEREQhWhWWB4WYqYT4ITUGX");
        // 文档中对应字段：scene_id，实际使用时请替换成真实参数
        param.setSceneID("5561515033704");
        // 文档中对应字段：scene_type，实际使用时请替换成真实参数
        param.setSceneType(4);
        // 文档中对应字段：pm_app_key，实际使用时请替换成真实参数
        param.setPmAppKey("WXF4pGOvo7TTGU0qCMMhEjvFBkF5bO4Z");
        UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail
            updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail =
                new UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail();
        updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail.setName("xxxx"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail.setStatus(
            "3"); // 文档中对应字段：Status，实际使用时请替换成真实参数
        updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail.setSwanSchema(
            "baiduboxapp://swan/B3GF7AWvCSr16myIs42uqaoYz2pPCSY1/wjz/bdxd/order-detail/order-detail?orderId=784370632213"); // 文档中对应字段：SwanSchema，实际使用时请替换成真实参数

        UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem
            updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem =
                new UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem();
        updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem.setAmount(
            100L); // 文档中对应字段：Amount，实际使用时请替换成真实参数
        updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem.setID(
            "024484020160"); // 文档中对应字段：ID，实际使用时请替换成真实参数
        updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem.setQuantity(
            1L); // 文档中对应字段：Quantity，实际使用时请替换成真实参数

        UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund
            updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund =
                new UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund();
        updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund.setAmount("7535"); // 文档中对应字段：Amount，实际使用时请替换成真实参数
        updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund.setProduct(
            new UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] {
                updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem
            }); // 文档中对应字段：Product，实际使用时请替换成真实参数

        UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem =
            new UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem();
        updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem.setCTime(1571026203L); // 文档中对应字段：CTime，实际使用时请替换成真实参数
        updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem.setMTime(1571026203L); // 文档中对应字段：MTime，实际使用时请替换成真实参数
        updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem.setOrderDetail(
            updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail); // 文档中对应字段：OrderDetail，实际使用时请替换成真实参数
        updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem.setOrderType(1L); // 文档中对应字段：OrderType，实际使用时请替换成真实参数
        updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem.setRefund(
            updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItemRefund); // 文档中对应字段：Refund，实际使用时请替换成真实参数
        updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem.setSubOrderID(
            "onlyOne"); // 文档中对应字段：SubOrderID，实际使用时请替换成真实参数
        updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem.setSubStatus(401L); // 文档中对应字段：SubStatus，实际使用时请替换成真实参数

        UpdateOrderSubInfoRequestDataItemEXTSubsOrder updateOrderSubInfoRequestDataItemEXTSubsOrder =
            new UpdateOrderSubInfoRequestDataItemEXTSubsOrder();
        updateOrderSubInfoRequestDataItemEXTSubsOrder.setItems(
            new UpdateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem[] {
                updateOrderSubInfoRequestDataItemEXTSubsOrderItemsItem
            }); // 文档中对应字段：Items，实际使用时请替换成真实参数

        updateOrderSubInfoRequestDataItemEXTSubsOrder.setStatus(0); // 文档中对应字段：Status，实际使用时请替换成真实参数

        UpdateOrderSubInfoRequestDataItemEXT updateOrderSubInfoRequestDataItemEXT =
            new UpdateOrderSubInfoRequestDataItemEXT();
        updateOrderSubInfoRequestDataItemEXT.setSubsOrder(
            updateOrderSubInfoRequestDataItemEXTSubsOrder); // 文档中对应字段：SubsOrder，实际使用时请替换成真实参数

        UpdateOrderSubInfoRequestDataItem updateOrderSubInfoRequestDataItem = new UpdateOrderSubInfoRequestDataItem();
        updateOrderSubInfoRequestDataItem.setBizAPPID(
            "WXF3pGOvo5TTGU6qCMMhEjvFBkF4bO0Z"); // 文档中对应字段：BizAPPID，实际使用时请替换成真实参数
        updateOrderSubInfoRequestDataItem.setCateID(1L); // 文档中对应字段：CateID，实际使用时请替换成真实参数
        updateOrderSubInfoRequestDataItem.setEXT(updateOrderSubInfoRequestDataItemEXT); // 文档中对应字段：EXT，实际使用时请替换成真实参数
        updateOrderSubInfoRequestDataItem.setResourceID("8564442361446"); // 文档中对应字段：ResourceID，实际使用时请替换成真实参数

        param.setData(
            new UpdateOrderSubInfoRequestDataItem[] {updateOrderSubInfoRequestDataItem}); // 文档中对应字段：Data，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppUpdateOrderSubInfo.updateOrderSubInfo(param)));
        } catch (UpdateOrderSubInfoException e) {
            e.printStackTrace();
        }
    }
}
