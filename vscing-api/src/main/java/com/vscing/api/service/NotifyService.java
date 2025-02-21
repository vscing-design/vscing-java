package com.vscing.api.service;

import jakarta.servlet.http.HttpServletRequest;

/**
 * NotifyService
 *
 * @author vscing
 * @date 2025/1/21 23:01
 */
public interface NotifyService {

  /**
   * 查询支付宝订单是否成功
  */
  boolean queryAlipayOrder(HttpServletRequest request);

  /**
   * 查询微信订单是否成功
   */
  boolean queryWechatOrder(HttpServletRequest request);

  /**
   * 查询百度订单是否成功
   */
  boolean queryBaiduOrder(HttpServletRequest request);

  /**
   * 出票
   */
  void ticketOrder(String orderSn);

}
