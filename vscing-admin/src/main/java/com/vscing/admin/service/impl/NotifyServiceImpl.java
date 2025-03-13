package com.vscing.admin.service.impl;

import com.vscing.admin.service.NotifyService;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.common.service.applet.impl.wechat.extend.Transfer;
import com.vscing.common.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * NotifyServiceImpl
 *
 * @author vscing
 * @date 2025/3/13 20:59
 */
@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {

  @Autowired
  private AppletServiceFactory appletServiceFactory;

  @Override
  public boolean alipayTransfer(HttpServletRequest request) {
    return false;
  }

  @Override
  public boolean wechatTransfer(HttpServletRequest request) {
    try {
      // 原始请求体内容
      String requestBody = RequestUtil.getRequestBody(request);
      log.error("微信转账异步通知请求参数: {}", requestBody);
      // 返回结果
      Map<String, String> params = new HashMap<>(6);
      params.put("wechatPaySerial", request.getHeader("Wechatpay-Serial"));
      params.put("wechatpayNonce", request.getHeader("Wechatpay-Nonce"));
      params.put("wechatSignature", request.getHeader("Wechatpay-Signature"));
      params.put("wechatTimestamp", request.getHeader("Wechatpay-Timestamp"));
      params.put("requestBody", requestBody);
      log.error("微信转账异步通知请求参数集合: {}", params);
      // 支付类
      AppletService appletService = appletServiceFactory.getAppletService("wechat");
      // 签名认证
      Transfer transfer = (Transfer) appletService.signValidation(params);
      if (!Objects.nonNull(transfer)) {
        throw new ServiceException("微信转账验证签名未通过");
      }
      log.error("微信转账异步通知数据解密: {}", transfer);
      return true;
    } catch (Exception e) {
      log.error("微信转账回调异常", e);
      return false;
    }
  }
}
