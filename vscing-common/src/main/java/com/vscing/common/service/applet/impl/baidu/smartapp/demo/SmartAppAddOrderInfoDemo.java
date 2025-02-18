// 本示例基于百度智能小程序服务端开发者 OpenAPI-SDK-Java
// 使用该示例需要首先下载该 SDK，使用引导见：https://smartprogram.baidu.com/docs/develop/serverapi/introduction_for_openapi_sdk/
// 使用之前请先确认下 SDK 版本是否为最新版本，如不是，请下载最新版本使用
// 如使用过程中遇到问题，可以加入如流群：5702992，进行反馈咨询
package com.vscing.common.service.applet.impl.baidu.smartapp.demo;

import com.google.gson.Gson;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoException;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequestDataItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequestDataItemEXT;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequestDataItemEXTMainOrder;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequestDataItemEXTMainOrderAppraise;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequestDataItemEXTMainOrderOrderDetail;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequestDataItemEXTMainOrderPayment;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequestDataItemEXTMainOrderProductsItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequestDataItemEXTSubsOrder;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequestDataItemEXTSubsOrderItemsItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.AddOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppAddOrderInfo;

public class SmartAppAddOrderInfoDemo {

    public static void main(String[] args) {
        AddOrderInfoRequest param = new AddOrderInfoRequest();
        // 开发者在此设置请求参数，实际参数请参考文档说明填写
        // 如果开发者不想传非必需参数，可以将设置该参数的行注释

        // 文档中对应字段：access_token，实际使用时请替换成真实参数
        param.setAccessToken("24.405f02124a0336a5c8ad0c623560eb32.5458503.8111435504.674168-68655517");
        // 文档中对应字段：open_id，实际使用时请替换成真实参数
        param.setOpenID("k40HEREQhWhWWB7WYqYT1ITUGX");
        // 文档中对应字段：swan_id，实际使用时请替换成真实参数
        param.setSwanID("xxxx");
        // 文档中对应字段：scene_id，实际使用时请替换成真实参数
        param.setSceneID("6470240117001");
        // 文档中对应字段：scene_type，实际使用时请替换成真实参数
        param.setSceneType(1);
        // 文档中对应字段：pm_app_key，实际使用时请替换成真实参数
        param.setPmAppKey("baiduboxapp");
        AddOrderInfoRequestDataItemEXTMainOrderOrderDetail addOrderInfoRequestDataItemEXTMainOrderOrderDetail =
            new AddOrderInfoRequestDataItemEXTMainOrderOrderDetail();
        addOrderInfoRequestDataItemEXTMainOrderOrderDetail.setName("test"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderOrderDetail.setStatus(0L); // 文档中对应字段：Status，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderOrderDetail.setSwanSchema(
            "baiduboxapp://swan/B5GF3AWvCSr01myIs06uqaoYz4pPCSY6/wjz/bdxd/order-detail/order-detail?orderId=086783806081"); // 文档中对应字段：SwanSchema，实际使用时请替换成真实参数

        AddOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem
            addOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem =
                new AddOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem();
        addOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem.setName(
            "四川大凉山丑苹果脆甜"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem.setValue(
            "5斤小果25个左右偏小"); // 文档中对应字段：Value，实际使用时请替换成真实参数

        AddOrderInfoRequestDataItemEXTMainOrderProductsItem addOrderInfoRequestDataItemEXTMainOrderProductsItem =
            new AddOrderInfoRequestDataItemEXTMainOrderProductsItem();
        addOrderInfoRequestDataItemEXTMainOrderProductsItem.setDesc(
            "四川大凉山丑苹果脆甜:5斤小果25个左右偏小;"); // 文档中对应字段：Desc，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderProductsItem.setID("3870885702774"); // 文档中对应字段：ID，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderProductsItem.setImgList(
            new String[] {"xxxx"}); // 文档中对应字段：ImgList，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderProductsItem.setName("四川大凉山丑苹果脆甜红将军盐源丑苹果"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderProductsItem.setPayPrice(2390L); // 文档中对应字段：PayPrice，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderProductsItem.setPrice(2390L); // 文档中对应字段：Price，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderProductsItem.setQuantity(1L); // 文档中对应字段：Quantity，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderProductsItem.setSkuAttr(
            new AddOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem[] {
                addOrderInfoRequestDataItemEXTMainOrderProductsItemSkuAttrItem
            }); // 文档中对应字段：SkuAttr，实际使用时请替换成真实参数

        AddOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem
            addOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem =
                new AddOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem();
        addOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem.setName(
            "优惠券使用"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem.setQuantity(
            1L); // 文档中对应字段：Quantity，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem.setValue(
            100L); // 文档中对应字段：Value，实际使用时请替换成真实参数

        AddOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem
            addOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem =
                new AddOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem();
        addOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem.setName("运费"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem.setQuantity(1L); // 文档中对应字段：Quantity，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem.setValue(100L); // 文档中对应字段：Value，实际使用时请替换成真实参数

        AddOrderInfoRequestDataItemEXTMainOrderPayment addOrderInfoRequestDataItemEXTMainOrderPayment =
            new AddOrderInfoRequestDataItemEXTMainOrderPayment();
        addOrderInfoRequestDataItemEXTMainOrderPayment.setAmount(2390L); // 文档中对应字段：Amount，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderPayment.setIsPayment(false); // IsPayment
        addOrderInfoRequestDataItemEXTMainOrderPayment.setMethod(1L); // 文档中对应字段：Method，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderPayment.setPaymentInfo(
            new AddOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem[] {
                addOrderInfoRequestDataItemEXTMainOrderPaymentPaymentInfoItem
            }); // 文档中对应字段：PaymentInfo，实际使用时请替换成真实参数

        addOrderInfoRequestDataItemEXTMainOrderPayment.setPreferentialInfo(
            new AddOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem[] {
                addOrderInfoRequestDataItemEXTMainOrderPaymentPreferentialInfoItem
            }); // 文档中对应字段：PreferentialInfo，实际使用时请替换成真实参数

        addOrderInfoRequestDataItemEXTMainOrderPayment.setTime(0L); // 文档中对应字段：Time，实际使用时请替换成真实参数

        AddOrderInfoRequestDataItemEXTMainOrderAppraise addOrderInfoRequestDataItemEXTMainOrderAppraise =
            new AddOrderInfoRequestDataItemEXTMainOrderAppraise();
        addOrderInfoRequestDataItemEXTMainOrderAppraise.setName("test"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderAppraise.setStatus(0L); // 文档中对应字段：Status，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrderAppraise.setSwanSchema(
            "baiduboxapp://swan/B8GF8AWvCSr63myIs32uqaoYz8pPCSY6/wjz/bdxd/order-detail/order-detail?orderId=400375355760"); // 文档中对应字段：SwanSchema，实际使用时请替换成真实参数

        AddOrderInfoRequestDataItemEXTMainOrder addOrderInfoRequestDataItemEXTMainOrder =
            new AddOrderInfoRequestDataItemEXTMainOrder();
        addOrderInfoRequestDataItemEXTMainOrder.setAppraise(
            addOrderInfoRequestDataItemEXTMainOrderAppraise); // 文档中对应字段：Appraise，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrder.setOrderDetail(
            addOrderInfoRequestDataItemEXTMainOrderOrderDetail); // 文档中对应字段：OrderDetail，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrder.setPayment(
            addOrderInfoRequestDataItemEXTMainOrderPayment); // 文档中对应字段：Payment，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTMainOrder.setProducts(
            new AddOrderInfoRequestDataItemEXTMainOrderProductsItem[] {
                addOrderInfoRequestDataItemEXTMainOrderProductsItem
            }); // 文档中对应字段：Products，实际使用时请替换成真实参数

        AddOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail
            addOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail =
                new AddOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail();
        addOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail.setName("test"); // 文档中对应字段：Name，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail.setStatus(2L); // 文档中对应字段：Status，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail.setSwanSchema(
            "baiduboxapp://swan/B5GF8AWvCSr72myIs17uqaoYz1pPCSY6/wjz/bdxd/order-detail/order-detail?orderId=471301664372"); // 文档中对应字段：SwanSchema，实际使用时请替换成真实参数

        AddOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem
            addOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem =
                new AddOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem();
        addOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem.setAmount(0L); // 文档中对应字段：Amount，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem.setID(
            "5807321644"); // 文档中对应字段：ID，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem.setQuantity(
            1L); // 文档中对应字段：Quantity，实际使用时请替换成真实参数

        AddOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund addOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund =
            new AddOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund();
        addOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund.setAmount(10L); // 文档中对应字段：Amount，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund.setProduct(
            new AddOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem[] {
                addOrderInfoRequestDataItemEXTSubsOrderItemsItemRefundProductItem
            }); // 文档中对应字段：Product，实际使用时请替换成真实参数

        AddOrderInfoRequestDataItemEXTSubsOrderItemsItem addOrderInfoRequestDataItemEXTSubsOrderItemsItem =
            new AddOrderInfoRequestDataItemEXTSubsOrderItemsItem();
        addOrderInfoRequestDataItemEXTSubsOrderItemsItem.setCTime(1571026201L); // 文档中对应字段：CTime，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTSubsOrderItemsItem.setMTime(1571026201L); // 文档中对应字段：MTime，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTSubsOrderItemsItem.setOrderDetail(
            addOrderInfoRequestDataItemEXTSubsOrderItemsItemOrderDetail); // 文档中对应字段：OrderDetail，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTSubsOrderItemsItem.setOrderType(1L); // 文档中对应字段：OrderType，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTSubsOrderItemsItem.setRefund(
            addOrderInfoRequestDataItemEXTSubsOrderItemsItemRefund); // 文档中对应字段：Refund，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTSubsOrderItemsItem.setSubOrderID("onlyOne"); // 文档中对应字段：SubOrderID，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXTSubsOrderItemsItem.setSubStatus("152"); // 文档中对应字段：SubStatus，实际使用时请替换成真实参数

        AddOrderInfoRequestDataItemEXTSubsOrder addOrderInfoRequestDataItemEXTSubsOrder =
            new AddOrderInfoRequestDataItemEXTSubsOrder();
        addOrderInfoRequestDataItemEXTSubsOrder.setItems(
            new AddOrderInfoRequestDataItemEXTSubsOrderItemsItem[] {
                addOrderInfoRequestDataItemEXTSubsOrderItemsItem
            }); // 文档中对应字段：Items，实际使用时请替换成真实参数

        addOrderInfoRequestDataItemEXTSubsOrder.setStatus(0L); // 文档中对应字段：Status，实际使用时请替换成真实参数

        AddOrderInfoRequestDataItemEXT addOrderInfoRequestDataItemEXT = new AddOrderInfoRequestDataItemEXT();
        addOrderInfoRequestDataItemEXT.setMainOrder(
            addOrderInfoRequestDataItemEXTMainOrder); // 文档中对应字段：MainOrder，实际使用时请替换成真实参数
        addOrderInfoRequestDataItemEXT.setSubsOrder(
            addOrderInfoRequestDataItemEXTSubsOrder); // 文档中对应字段：SubsOrder，实际使用时请替换成真实参数

        AddOrderInfoRequestDataItem addOrderInfoRequestDataItem = new AddOrderInfoRequestDataItem();
        addOrderInfoRequestDataItem.setBizAPPID("WXF7pGOvo7TTGU6qCMMhEjvFBkF2bO1Z"); // 文档中对应字段：BizAPPID，实际使用时请替换成真实参数
        addOrderInfoRequestDataItem.setCateID(1L); // 文档中对应字段：CateID，实际使用时请替换成真实参数
        addOrderInfoRequestDataItem.setCtime(1233212343L); // 文档中对应字段：Ctime，实际使用时请替换成真实参数
        addOrderInfoRequestDataItem.setEXT(addOrderInfoRequestDataItemEXT); // 文档中对应字段：EXT，实际使用时请替换成真实参数
        addOrderInfoRequestDataItem.setMtime(1233212343L); // 文档中对应字段：Mtime，实际使用时请替换成真实参数
        addOrderInfoRequestDataItem.setResourceID("4355234802422"); // 文档中对应字段：ResourceID，实际使用时请替换成真实参数
        addOrderInfoRequestDataItem.setStatus(200L); // 文档中对应字段：Status，实际使用时请替换成真实参数
        addOrderInfoRequestDataItem.setTitle("test"); // 文档中对应字段：Title，实际使用时请替换成真实参数

        param.setData(new AddOrderInfoRequestDataItem[] {addOrderInfoRequestDataItem}); // 文档中对应字段：Data，实际使用时请替换成真实参数

        try {
            System.out.println(new Gson().toJson(SmartAppAddOrderInfo.addOrderInfo(param)));
        } catch (AddOrderInfoException e) {
            e.printStackTrace();
        }
    }
}
