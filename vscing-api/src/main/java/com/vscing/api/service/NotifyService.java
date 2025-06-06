package com.vscing.api.service;

import com.vscing.model.dto.BaiduCreateNotifyDto;
import com.vscing.model.dto.BaiduRefundNotifyDto;
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
   * 查询微信转账是否成功
   */
  boolean queryWechatTransfer(HttpServletRequest request);

  /**
   * 查询百度订单是否成功
   */
  boolean queryBaiduOrder(BaiduCreateNotifyDto baiduCreateNotifyDto);

  /**
   * 查询百度退款订单是否成功
   */
  boolean queryBaiduRefund(BaiduRefundNotifyDto baiduRefundNotifyDto);

  /**
   * 三方电影票下单
   */
  void ticketOrder(String orderSn);

  /**
   * 三方会员商品下单
   */
  void ticketVipOrder(String orderSn);

}
