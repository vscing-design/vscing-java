package com.vscing.common.service.payment.impl.alipay;

import com.vscing.common.service.payment.PaymentService;
import org.springframework.stereotype.Service;

/**
 * AlipayPaymentServiceImpl
 *
 * @author vscing
 * @date 2024/12/26 23:03
 */
@Service("alipayPaymentService")
public class AlipayPaymentServiceImpl implements PaymentService {

  @Override
  public String pay(double amount) {
    return "Alipay payment of " + amount + " completed.";
  }

}
