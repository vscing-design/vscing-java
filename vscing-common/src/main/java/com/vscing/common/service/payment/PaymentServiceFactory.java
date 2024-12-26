package com.vscing.common.service.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * PaymentServiceFactory
 *
 * @author vscing
 * @date 2024/12/26 23:01
 */
@Component
public class PaymentServiceFactory {

  private final Map<String, PaymentService> paymentServices;

  @Autowired
  public PaymentServiceFactory(Map<String, PaymentService> paymentServices) {
    this.paymentServices = paymentServices;
  }

  public PaymentService getPaymentService(String type) {
    if ("alipay".equalsIgnoreCase(type)) {
      return paymentServices.get("alipayService");
    } else if ("wechat".equalsIgnoreCase(type)) {
      return paymentServices.get("wechatPayService");
    } else if ("unionpay".equalsIgnoreCase(type)) {
      return paymentServices.get("unionPayService");
    } else {
      throw new IllegalArgumentException("Unknown payment type: " + type);
    }
  }
}


