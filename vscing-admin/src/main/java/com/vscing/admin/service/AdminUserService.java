package com.vscing.admin.service;

import com.vscing.auth.service.VscingUserDetails;

/**
 * AdminUserService
 *
 * @author vscing
 * @date 2024/12/14 21:08
 */
public interface AdminUserService {

  VscingUserDetails adminUserInfo(long id);

  /**
   * 登录后获取token
   */
  String login(String username, String password);
}
