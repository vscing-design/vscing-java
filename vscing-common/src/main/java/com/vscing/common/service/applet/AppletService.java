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
  String getPayment(Map<String, Object> paymentData);

  /**
   * 查询小程序订单是否支付成功
   */
  boolean queryOrder(Map<String, String> queryData);

}
