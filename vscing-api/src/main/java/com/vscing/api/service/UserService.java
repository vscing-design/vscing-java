package com.vscing.api.service;

import com.vscing.auth.service.VscingUserDetails;
import com.vscing.model.dto.UserInviteQrcodeDto;
import com.vscing.model.dto.UserLoginDto;
import com.vscing.model.mq.InviteMq;
import com.vscing.model.mq.RebateMq;
import com.vscing.model.vo.UserApiLocationVo;
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
   * 测试小程序用户登录
   */
  String testLogin();

  /**
   * 小程序用户登陆
   */
  String login(UserLoginDto userLogin, HttpServletRequest request);

  /**
   * 用户手机号
   */
  String userPhone(UserLoginDto userLogin, UserDetailVo userData, String authToken);

  /**
   * 用户邀请
   */
  void userInvite(InviteMq inviteMq);

  /**
   * 邀请用户消费
  */
  void userRebate(RebateMq rebateMq);

  /**
   * 用户推广二维码
   */
  String inviteQrcode(UserInviteQrcodeDto userInviteQrcode, UserDetailVo user);

  /**
   * 获取当前用户的经纬度
   */
  UserApiLocationVo getLocation(HttpServletRequest request);

  /**
   * 退出登陆
   */
  boolean logout(UserDetailVo user, String authToken);

}
