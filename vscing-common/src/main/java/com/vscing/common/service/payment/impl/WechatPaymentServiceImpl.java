package com.vscing.common.service.payment.impl;

import com.vscing.common.service.payment.PaymentService;
import org.springframework.stereotype.Service;

/**
 * WechatPaymentServiceImpl
 *
 * @author vscing
 * @date 2024/12/26 23:09
 */
@Service("wechatPaymentService")
public class WechatPaymentServiceImpl implements PaymentService {

  @Override
  public String pay(double amount) {
    return "Wechat payment of " + amount + " completed.";
  }

}
