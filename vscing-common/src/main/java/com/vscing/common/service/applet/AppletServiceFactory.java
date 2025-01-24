package com.vscing.common.service.applet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * AppletServiceFactory
 *
 * @author vscing
 * @date 2025/1/7 19:21
 */
@Component
public class AppletServiceFactory {

  public static final String WECHAT = "wechat";

  public static final String ALIPAY = "alipay";

  private final Map<String, AppletService> appletServices;

  @Autowired
  public AppletServiceFactory(Map<String, AppletService> appletServices) {
    this.appletServices = appletServices;
  }

  public AppletService getAppletService(String type) {
    if (WECHAT.equalsIgnoreCase(type)) {
      return this.appletServices.get("wechatAppletService");
    } else if (ALIPAY.equalsIgnoreCase(type)) {
      return this.appletServices.get("alipayAppletService");
    } else {
      throw new IllegalArgumentException("Unknown payment type: " + type);
    }
  }

}
