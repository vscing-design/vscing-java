package com.vscing.common.service.applet;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

/**
 * AppletService
 * 小程序封装
 * @date 2024/12/10 23:54
 * @auth vscing(vscing @ foxmail.com)
 */
public interface AppletService {

  /**
   * 获取openid
  */
  JsonNode getOpenid(String code);

  /**
   * 获取手机号
  */
  String getPhoneNumber(String code);
  
  /** 
   * 获取小程序支付参数
  */
  Map<String, String> getPayment(Map<String, Object> paymentData);

  /**
   * 小程序签名验证
   */
  Object signValidation(Map<String, String> params);

  /**
   * 查询小程序订单是否支付成功
   */
  boolean queryOrder(Map<String, String> queryData);

  /**
   * 小程序退款
   */
  boolean refundOrder(Map<String, Object> refundData);

  /**
   * 查询小程序订单是否退款成功
   */
  boolean queryRefund(Map<String, String> queryData);

  /**
   * 获取小程序二维码
   */
  String getQrcode(Map<String, Object> queryData);

  /**
   * 小程序转账
   * 发起转账。需要参数：批次单号、金额、openid、回调地址、备注等等信息。
   * 返回微信、支付宝那边的单号
   */
  Object transferOrder(Map<String, Object> transferData);

  /**
   * 同步订单
  */
  void syncOrder(Map<String, Object> syncOrderData);

}
