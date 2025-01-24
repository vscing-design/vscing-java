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

  public static final String WECHAT = "wechat";

  public static final String ALIPAY = "alipay";

  public static final String UNIONPAY = "unionPay";

  private final Map<String, PaymentService> paymentServices;

  @Autowired
  public PaymentServiceFactory(Map<String, PaymentService> paymentServices) {
    this.paymentServices = paymentServices;
  }

  public PaymentService getPaymentService(String type) {
    if (ALIPAY.equalsIgnoreCase(type)) {
      return this.paymentServices.get("alipayPaymentService");
    } else if (WECHAT.equalsIgnoreCase(type)) {
      return this.paymentServices.get("wechatPaymentService");
    } else if (UNIONPAY.equalsIgnoreCase(type)) {
      return this.paymentServices.get("unionpayPaymentService");
    } else {
      throw new IllegalArgumentException("Unknown payment type: " + type);
    }
  }
}


