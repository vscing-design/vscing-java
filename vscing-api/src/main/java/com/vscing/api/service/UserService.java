package com.vscing.api.service;

import com.vscing.auth.service.VscingUserDetails;
import com.vscing.model.dto.UserLoginDto;
import com.vscing.model.vo.UserDetailVo;
import jakarta.servlet.http.HttpServletRequest;

/**
 * UserService
 *
 * @author vscing
 * @date 2025/1/6 11:47
 */
public interface UserService {

  /**
   * 搭配springSecurity权限
   */
  VscingUserDetails userInfo(long id);

  /**
   * 用户信息
  */
  UserDetailVo self(long id);

  /**
   * 小程序用户登陆
  */
  String login(UserLoginDto userLogin, HttpServletRequest request);

  /**
   * 用户手机号
  */
  String userPhone(UserLoginDto userLogin, UserDetailVo userData, String authToken);

  /**
   * 退出登陆
  */
  boolean logout(UserDetailVo user, String authToken);

}
