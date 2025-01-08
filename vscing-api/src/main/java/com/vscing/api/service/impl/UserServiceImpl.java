package com.vscing.api.service.impl;

import com.vscing.api.po.impl.UserDetailsImpl;
import com.vscing.api.service.UserService;
import com.vscing.auth.service.VscingUserDetails;
import com.vscing.auth.util.JwtTokenUtil;
import com.vscing.common.service.RedisService;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.model.dto.UserLoginDto;
import com.vscing.model.entity.User;
import com.vscing.model.mapper.UserMapper;
import com.vscing.model.vo.UserDetailVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * UserService
 *
 * @author vscing
 * @date 2025/1/6 11:48
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

  @Value("${jwt.expiration}")
  private Long expiration;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private RedisService redisService;

  @Autowired
  private AppletServiceFactory appletServiceFactory;

  @Override
  public VscingUserDetails userInfo(long id) {
    // 获取用户信息
    UserDetailVo userData = this.self(id);
    return new UserDetailsImpl(userData);
  }

  @Override
  public UserDetailVo self(long id) {
    // 获取用户信息
    User entity = userMapper.selectById(id);
    // 直接调用改进后的 Mapper 方法进行转换
    UserDetailVo userData = MapstructUtils.convert(entity, UserDetailVo.class);

    return userData;
  }

  @Override
  public String login(UserLoginDto userLogin, HttpServletRequest request) {
    String phone = null;
    try {
      AppletService appletService = appletServiceFactory.getAppletService(userLogin.getPlatform());
      phone = appletService.getPhoneNumber(userLogin.getCode());
    } catch (Exception e) {
      log.error("", e);
    }
    return phone;
  }

}
