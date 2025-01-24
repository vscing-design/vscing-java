package com.vscing.api.service.impl;

import com.vscing.api.service.NotifyService;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.model.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * NotifyServiceImpl
 *
 * @author vscing
 * @date 2025/1/21 23:03
 */
@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {

  @Autowired
  private AppletServiceFactory appletServiceFactory;

  @Autowired
  private OrderMapper orderMapper;

  @Override
  public boolean queryAlipayOrder(Map<String, String> params) {
    try {
      // 查询支付宝订单
      AppletService appletService = appletServiceFactory.getAppletService("alipay");
      boolean res = appletService.queryOrder(params);
      if (!res) {
        throw new ServiceException("支付宝订单支付未成功");
      }
      String orderSn = params.get("outTradeNo");
      String tradeNo = params.get("tradeNo");
      // 修改订单
      int rowsAffected = orderMapper.updateAlipayOrder(orderSn, tradeNo);
      if (rowsAffected <= 0) {
        throw new ServiceException("修改支付宝订单状态失败");
      }
      return true;
    } catch (Exception e) {
      log.error("支付宝回调异常: {}", e);
      return false;
    }
  }

}
