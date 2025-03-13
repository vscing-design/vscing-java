package com.vscing.admin.service;

import jakarta.servlet.http.HttpServletRequest;

/**
 * NotifyService
 *
 * @author vscing
 * @date 2025/3/13 20:59
 */
public interface NotifyService {

  /**
   * 支付宝转账异步通知
   */
  boolean alipayTransfer(HttpServletRequest request);

  /**
   * 微信转账异步通知
  */
  boolean wechatTransfer(HttpServletRequest request);

}
