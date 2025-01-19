package com.vscing.common.service.payment.impl.union;

import com.vscing.common.service.payment.PaymentService;
import org.springframework.stereotype.Service;

/**
 * UnionpayPaymentServiceImpl
 *
 * @author vscing
 * @date 2024/12/26 23:49
 */
@Service("unionpayPaymentService")
public class UnionpayPaymentServiceImpl implements PaymentService {

  @Override
  public String pay(double amount) {
    return "Unionpay payment of " + amount + " completed.";
  }

}
