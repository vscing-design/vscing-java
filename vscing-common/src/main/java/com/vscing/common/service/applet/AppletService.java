package com.vscing.common.service.applet;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * AppletService
 * 小程序封装
 * @date 2024/12/10 23:54
 * @auth vscing(vscing @ foxmail.com)
 */
public interface AppletService {

  /**
   * 获取openid
  */
  JsonNode getOpenid(String code);

  /**
   * 获取手机号
  */
  String getPhoneNumber(String code);

}
