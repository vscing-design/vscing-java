package com.vscing.api.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.vscing.api.po.impl.UserDetailsImpl;
import com.vscing.api.service.UserService;
import com.vscing.auth.service.VscingUserDetails;
import com.vscing.auth.util.JwtTokenUtil;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.RedisService;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.common.utils.RequestUtil;
import com.vscing.model.dto.UserLoginDto;
import com.vscing.model.entity.User;
import com.vscing.model.entity.UserAuth;
import com.vscing.model.mapper.UserAuthMapper;
import com.vscing.model.mapper.UserMapper;
import com.vscing.model.vo.UserDetailVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

  @Value("${jwt.cachePrefix}")
  private String cachePrefix;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserAuthMapper userAuthMapper;

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
    String token = null;
    try {
      AppletService appletService = appletServiceFactory.getAppletService(userLogin.getPlatform());
      JsonNode jsonNode = appletService.getOpenid(userLogin.getCode());
      String openid = null;
      String uuid = null;
      Integer platform = 0;
      // 处理微信参数
      if(AppletServiceFactory.WECHAT.equals(userLogin.getPlatform())) {
        platform = 1;
        openid = jsonNode.path("openid").asText(null);
        uuid = jsonNode.path("unionid").asText(null);
      }
      // 处理支付宝参数
      if(AppletServiceFactory.ALIPAY.equals(userLogin.getPlatform())) {
        jsonNode = jsonNode.path("alipay_system_oauth_token_response");
        if (!jsonNode.isMissingNode()) {
          platform = 2;
          openid = jsonNode.path("open_id").asText(null);
          uuid = jsonNode.path("user_id").asText(null);
        }
      }
      // 判断参数是否存在
      if(openid == null && uuid == null) {
        throw new ServiceException("openid和uuid参数异常");
      }
      // 判断是否存在用户授权表，存在就根据用户授权表id拿到用户信息
      UserAuth userAuth = userAuthMapper.selectUserIdByOpenidAndUuid(platform, openid, uuid);
      // 获取用户id
      Long userId;
      if(userAuth == null) {
        // 不存在就创建用户
        userId = this.createUserAndAuth(platform, openid, uuid, RequestUtil.getRequestIp(request));
      } else {
        // 更新登陆记录
        userAuthMapper.updateLoginInfoById(userAuth.getId(), RequestUtil.getRequestIp(request), LocalDateTime.now());
        userId = userAuth.getUserId();
      }
      // 根据用户信息生成token
      UserDetailVo userData = this.self(userId);
      VscingUserDetails userDetails = new UserDetailsImpl(userData);
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      token = jwtTokenUtil.generateToken(userDetails.getUserId());
      // 记录到redis
      redisService.set(cachePrefix + userDetails.getUserId() + ":" + token, userData, expiration);
    } catch (Exception e) {
      log.error("login登陆异常：", e);
    }
    return token;
  }

  @Transactional(rollbackFor = Exception.class)
  public Long createUserAndAuth(Integer platform, String openid, String uuid, String loginIp) {
    Long userId = IdUtil.getSnowflakeNextId();
    try {
      User user = new User();
      user.setId(userId);
      user.setSource(platform);
      user.setUsername("HY_" + RandomUtil.randomString(8));
      // 创建用户
      int rowsAffected = userMapper.insert(user);
      if (rowsAffected <= 0) {
        throw new ServiceException("新增用户失败");
      }
      // 创建用户授权
      UserAuth userAuth = new UserAuth();
      userAuth.setId(IdUtil.getSnowflakeNextId());
      userAuth.setUserId(userId);
      userAuth.setPlatform(platform);
      userAuth.setOpenid(openid);
      userAuth.setUuid(uuid);
      userAuth.setLastIp(loginIp);
      userAuth.setLoginAt(LocalDateTime.now());
      rowsAffected = userAuthMapper.insert(userAuth);
      if (rowsAffected <= 0) {
        throw new ServiceException("新增用户授权失败");
      }
      return userId;
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
  }

  @Override
  public String userPhone(UserLoginDto userLogin, UserDetailVo oldUserData, String authToken) {
    try {
      AppletService appletService = appletServiceFactory.getAppletService(userLogin.getPlatform());
      String phone = appletService.getPhoneNumber(userLogin.getCode());
      User newUserData = userMapper.selectByPhone(phone);

      if (newUserData != null && !newUserData.getId().equals(oldUserData.getId())) {
        // 如果手机号存在并且不是当前用户，删除已存在用户id，更新用户三方授权表用户id
        boolean res = this.changeUser(newUserData.getId(), oldUserData.getId());
        if(res) {
          this.logout(oldUserData, authToken);
        } else {
          throw new ServiceException("如果手机号存在并且不是当前用户，删除已存在用户id，更新用户三方授权表用户id 失败");
        }
      } else if (newUserData == null) {
        // 如果手机号用户不存在
        User updateUser = new User();
        updateUser.setId(oldUserData.getId());
        updateUser.setPhone(phone);
        updateUser.setUpdatedBy(oldUserData.getId());
        int rowsAffected = userMapper.updatePhone(updateUser);
        if (rowsAffected <= 0) {
          throw new ServiceException("用户授权手机号失败");
        }
      }
      return phone;
    } catch (Exception e) {
      log.error("用户授权手机号异常：", e);
      return null;
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public boolean changeUser(Long newUserId, Long oldUserId) {
    try {
      // 删除旧用户信息
      int rowsAffected = userMapper.softDeleteById(oldUserId, oldUserId);
      if (rowsAffected <= 0) {
        throw new ServiceException("删除用户失败");
      }
      // 修改用户授权表的userId
      rowsAffected = userAuthMapper.updateUserId(newUserId, oldUserId);
      if (rowsAffected <= 0) {
        throw new ServiceException("用户授权修改新用户ID失败");
      }
      return true;
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
  }

  @Override
  public boolean logout(UserDetailVo user, String authToken) {
    // 删除缓存
    String redisKey = cachePrefix + user.getId() + ":" + authToken;
    return redisService.del(redisKey);
  }

}
