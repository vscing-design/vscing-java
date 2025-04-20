package com.vscing.merchant.service;

import com.vscing.model.vo.MerchantDetailVo;
import jakarta.servlet.http.HttpServletRequest;

/**
 * UserService
 * @date 2025/4/20 21:37
 * @author vscing
 */
public interface UserService {

  /**
   * 登录后获取token
   */
  String login(String username, String password, HttpServletRequest request);

  /**
   * 登出
   */
  boolean logout(MerchantDetailVo merchantDetailVo, String authToken);

}
