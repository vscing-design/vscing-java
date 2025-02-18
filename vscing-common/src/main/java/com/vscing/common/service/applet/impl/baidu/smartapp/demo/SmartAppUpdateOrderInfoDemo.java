// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppUpdateOrderInfo;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequestDataItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequestDataItemEXT;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequestDataItemEXTMainOrder;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequestDataItemEXTMainOrderAppraise;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequestDataItemEXTMainOrderOrderDetail;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequestDataItemEXTMainOrderPayment;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequestDataItemEXTMainOrderProductsItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequestDataItemEXTSubsOrder;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem;

public class SmartAppUpdateOrderInfoDemo {

    public static void main(String[] args) {
        UpdateOrderInfoRequest param = new UpdateOrderInfoRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("61.466f71260a5054a7c1ad7c183612eb33.2037083.4465751811.055307-24323500");
        // 文档中对应字段：open_id，实际使用时请替换成真实参数
        param.setOpenID("k43HEREQhWhWWB4WYqYT0ITUGX");
        // 文档中对应字段：swan_id，实际使用时请替换成真实参数
        param.setSwanID("xxxx");
        // 文档中对应字段：scene_id，实际使用时请替换成真实参数
        param.setSceneID("2260843377460");
        // 文档中对应字段：scene_type，实际使用时请替换成真实参数
        param.setSceneType(7);
        // 文档中对应字段：pm_app_key，实际使用时请替换成真实参数
        param.setPmAppKey("baiduboxapp");
        UpdateOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem
            updateOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem =
                new UpdateOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem();
        updateOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem.setName(
            "四川大凉山丑苹果脆甜"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem.setValue(
            "5斤小果25个左右偏小"); // 文档中对应字段：Value，实际使用时请替换成真实参数

        UpdateOrderInfoRequestDataItemEXTMainOrderProductsItem updateOrderInfoRequestDataItemEXTMainOrderProductsItem =
            new UpdateOrderInfoRequestDataItemEXTMainOrderProductsItem();
        updateOrderInfoRequestDataItemEXTMainOrderProductsItem.setDesc(
            "四川大凉山丑苹果脆甜:5斤小果25个左右偏小;"); // 文档中对应字段：Desc，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderProductsItem.setID("8857238650581"); // 文档中对应字段：ID，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderProductsItem.setImgList(
            new String[] {"xxxx"}); // 文档中对应字段：ImgList，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderProductsItem.setName(
            "四川大凉山丑苹果脆甜红将军盐源丑苹果"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderProductsItem.setPayPrice(2390L); // 文档中对应字段：PayPrice，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderProductsItem.setPrice(2390L); // 文档中对应字段：Price，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderProductsItem.setQuantity(1L); // 文档中对应字段：Quantity，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderProductsItem.setSkuAttr(
            new UpdateOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem[] {
                updateOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem
            }); // 文档中对应字段：SkuAttr，实际使用时请替换成真实参数

        UpdateOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem
            updateOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem =
                new UpdateOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem();
        updateOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem.setName(
            "优惠券使用"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem.setQuantity(
            1L); // 文档中对应字段：Quantity，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem.setValue(
            100L); // 文档中对应字段：Value，实际使用时请替换成真实参数

        UpdateOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem
            updateOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem =
                new UpdateOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem();
        updateOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem.setName("运费"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem.setQuantity(
            1L); // 文档中对应字段：Quantity，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem.setValue(100L); // 文档中对应字段：Value，实际使用时请替换成真实参数

        UpdateOrderInfoRequestDataItemEXTMainOrderPayment updateOrderInfoRequestDataItemEXTMainOrderPayment =
            new UpdateOrderInfoRequestDataItemEXTMainOrderPayment();
        updateOrderInfoRequestDataItemEXTMainOrderPayment.setAmount(2390L); // 文档中对应字段：Amount，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderPayment.setIsPayment(false); // IsPayment
        updateOrderInfoRequestDataItemEXTMainOrderPayment.setMethod(1L); // 文档中对应字段：Method，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderPayment.setPaymentInfo(
            new UpdateOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem[] {
                updateOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem
            }); // 文档中对应字段：PaymentInfo，实际使用时请替换成真实参数

        updateOrderInfoRequestDataItemEXTMainOrderPayment.setPreferentialInfo(
            new UpdateOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem[] {
                updateOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem
            }); // 文档中对应字段：PreferentialInfo，实际使用时请替换成真实参数

        updateOrderInfoRequestDataItemEXTMainOrderPayment.setTime(0L); // 文档中对应字段：Time，实际使用时请替换成真实参数

        UpdateOrderInfoRequestDataItemEXTMainOrderAppraise updateOrderInfoRequestDataItemEXTMainOrderAppraise =
            new UpdateOrderInfoRequestDataItemEXTMainOrderAppraise();
        updateOrderInfoRequestDataItemEXTMainOrderAppraise.setName("xxxx"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderAppraise.setStatus(0L); // 文档中对应字段：Status，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderAppraise.setSwanSchema(
            "baiduboxapp://swan/B6GF0AWvCSr21myIs37uqaoYz1pPCSY6/wjz/bdxd/order-detail/order-detail?orderId=867138501141"); // 文档中对应字段：SwanSchema，实际使用时请替换成真实参数

        UpdateOrderInfoRequestDataItemEXTMainOrderOrderDetail updateOrderInfoRequestDataItemEXTMainOrderOrderDetail =
            new UpdateOrderInfoRequestDataItemEXTMainOrderOrderDetail();
        updateOrderInfoRequestDataItemEXTMainOrderOrderDetail.setName("xxxx"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderOrderDetail.setStatus(2L); // 文档中对应字段：Status，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrderOrderDetail.setSwanSchema(
            "baiduboxapp://swan/B1GF4AWvCSr17myIs71uqaoYz3pPCSY5/wjz/bdxd/order-detail/order-detail?orderId=743007262886"); // 文档中对应字段：SwanSchema，实际使用时请替换成真实参数

        UpdateOrderInfoRequestDataItemEXTMainOrder updateOrderInfoRequestDataItemEXTMainOrder =
            new UpdateOrderInfoRequestDataItemEXTMainOrder();
        updateOrderInfoRequestDataItemEXTMainOrder.setAppraise(
            updateOrderInfoRequestDataItemEXTMainOrderAppraise); // 文档中对应字段：Appraise，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrder.setOrderDetail(
            updateOrderInfoRequestDataItemEXTMainOrderOrderDetail); // 文档中对应字段：OrderDetail，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrder.setPayment(
            updateOrderInfoRequestDataItemEXTMainOrderPayment); // 文档中对应字段：Payment，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTMainOrder.setProducts(
            new UpdateOrderInfoRequestDataItemEXTMainOrderProductsItem[] {
                updateOrderInfoRequestDataItemEXTMainOrderProductsItem
            }); // 文档中对应字段：Products，实际使用时请替换成真实参数

        UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail
            updateOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail =
                new UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail();
        updateOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail.setName("test"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail.setStatus(2L); // 文档中对应字段：Status，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail.setSwanSchema(
            "baiduboxapp://swan/B5GF8AWvCSr65myIs63uqaoYz1pPCSY2/wjz/bdxd/order-detail/order-detail?orderId=482408224672"); // 文档中对应字段：SwanSchema，实际使用时请替换成真实参数

        UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem
            updateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem =
                new UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem();
        updateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem.setAmount(
            0L); // 文档中对应字段：Amount，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem.setID(
            "7567548166"); // 文档中对应字段：ID，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem.setQuantity(
            1L); // 文档中对应字段：Quantity，实际使用时请替换成真实参数

        UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund
            updateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund =
                new UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund();
        updateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund.setAmount(10L); // 文档中对应字段：Amount，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund.setProduct(
            new UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] {
                updateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem
            }); // 文档中对应字段：Product，实际使用时请替换成真实参数

        UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItem updateOrderInfoRequestDataItemEXTSubsOrderItemsItem =
            new UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItem();
        updateOrderInfoRequestDataItemEXTSubsOrderItemsItem.setCTime(1571026201L); // 文档中对应字段：CTime，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTSubsOrderItemsItem.setMTime(1571026201L); // 文档中对应字段：MTime，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTSubsOrderItemsItem.setOrderDetail(
            updateOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail); // 文档中对应字段：OrderDetail，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTSubsOrderItemsItem.setOrderType(1L); // 文档中对应字段：OrderType，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTSubsOrderItemsItem.setRefund(
            updateOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund); // 文档中对应字段：Refund，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTSubsOrderItemsItem.setSubOrderID(
            "onlyOne"); // 文档中对应字段：SubOrderID，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXTSubsOrderItemsItem.setSubStatus("727"); // 文档中对应字段：SubStatus，实际使用时请替换成真实参数

        UpdateOrderInfoRequestDataItemEXTSubsOrder updateOrderInfoRequestDataItemEXTSubsOrder =
            new UpdateOrderInfoRequestDataItemEXTSubsOrder();
        updateOrderInfoRequestDataItemEXTSubsOrder.setItems(
            new UpdateOrderInfoRequestDataItemEXTSubsOrderItemsItem[] {
                updateOrderInfoRequestDataItemEXTSubsOrderItemsItem
            }); // 文档中对应字段：Items，实际使用时请替换成真实参数

        updateOrderInfoRequestDataItemEXTSubsOrder.setStatus(0L); // 文档中对应字段：Status，实际使用时请替换成真实参数

        UpdateOrderInfoRequestDataItemEXT updateOrderInfoRequestDataItemEXT = new UpdateOrderInfoRequestDataItemEXT();
        updateOrderInfoRequestDataItemEXT.setMainOrder(
            updateOrderInfoRequestDataItemEXTMainOrder); // 文档中对应字段：MainOrder，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItemEXT.setSubsOrder(
            updateOrderInfoRequestDataItemEXTSubsOrder); // 文档中对应字段：SubsOrder，实际使用时请替换成真实参数

        UpdateOrderInfoRequestDataItem updateOrderInfoRequestDataItem = new UpdateOrderInfoRequestDataItem();
        updateOrderInfoRequestDataItem.setBizAPPID(
            "WXF6pGOvo6TTGU7qCMMhEjvFBkF3bO0Z"); // 文档中对应字段：BizAPPID，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItem.setCateID(1L); // 文档中对应字段：CateID，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItem.setCtime(1233212343L); // 文档中对应字段：Ctime，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItem.setEXT(updateOrderInfoRequestDataItemEXT); // 文档中对应字段：EXT，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItem.setMtime(1233212343L); // 文档中对应字段：Mtime，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItem.setResourceID("3103644858034"); // 文档中对应字段：ResourceID，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItem.setStatus(200L); // 文档中对应字段：Status，实际使用时请替换成真实参数
        updateOrderInfoRequestDataItem.setTitle("test"); // 文档中对应字段：Title，实际使用时请替换成真实参数

        param.setData(
            new UpdateOrderInfoRequestDataItem[] {updateOrderInfoRequestDataItem}); // 文档中对应字段：Data，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppUpdateOrderInfo.updateOrderInfo(param)));
        } catch (UpdateOrderInfoException e) {
            e.printStackTrace();
        }
    }
}
