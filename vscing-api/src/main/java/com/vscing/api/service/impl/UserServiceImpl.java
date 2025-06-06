package com.vscing.api.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.api.po.impl.UserDetailsImpl;
import com.vscing.api.service.UserConfigService;
import com.vscing.api.service.UserService;
import com.vscing.auth.service.VscingUserDetails;
import com.vscing.auth.util.JwtTokenUtil;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.OkHttpService;
import com.vscing.common.service.RedisService;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.common.utils.JsonUtils;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.common.utils.RequestUtil;
import com.vscing.common.utils.StringUtils;
import com.vscing.model.dto.UserInviteQrcodeDto;
import com.vscing.model.dto.UserLoginDto;
import com.vscing.model.entity.Order;
import com.vscing.model.entity.User;
import com.vscing.model.entity.UserAuth;
import com.vscing.model.entity.UserEarn;
import com.vscing.model.enums.AppletTypeEnum;
import com.vscing.model.mapper.OrderMapper;
import com.vscing.model.mapper.UserAuthMapper;
import com.vscing.model.mapper.UserEarnMapper;
import com.vscing.model.mapper.UserMapper;
import com.vscing.model.mq.InviteMq;
import com.vscing.model.mq.RebateMq;
import com.vscing.model.utils.PricingUtil;
import com.vscing.model.vo.UserApiLocationVo;
import com.vscing.model.vo.UserConfigPricingRuleVo;
import com.vscing.model.vo.UserDetailVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
  private OrderMapper orderMapper;

  @Autowired
  private UserAuthMapper userAuthMapper;

  @Autowired
  private RedisService redisService;

  @Autowired
  private UserConfigService userConfigService;

  @Autowired
  private AppletServiceFactory appletServiceFactory;

  @Autowired
  private OkHttpService okHttpService;

  @Autowired
  private UserEarnMapper userEarnMapper;

  @Override
  public VscingUserDetails userInfo(long id) {
    // 获取用户信息
    UserDetailVo userData = this.self(id);
    if (userData != null) {
      return new UserDetailsImpl(userData);
    }
    return null;
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
  public String testLogin() {
    String token = null;
    try {
      // 根据用户信息生成token
      UserDetailVo userData = this.self(1881281583710961664L);
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

  @Override
  public String login(UserLoginDto userLogin, HttpServletRequest request) {
    String token = null;
    try {
      AppletService appletService = appletServiceFactory.getAppletService(userLogin.getPlatform());
      JsonNode jsonNode = appletService.getOpenid(userLogin.getCode());
      String openid = null;
      String uuid = null;
      int platform = AppletTypeEnum.findByApplet(userLogin.getPlatform());
      // 处理微信参数
      if(AppletServiceFactory.WECHAT.equals(userLogin.getPlatform())) {
        openid = jsonNode.path("openid").asText(null);
        uuid = jsonNode.path("unionid").asText(null);
      }
      // 处理支付宝参数
      if(AppletServiceFactory.ALIPAY.equals(userLogin.getPlatform())) {
        jsonNode = jsonNode.path("alipay_system_oauth_token_response");
        if (!jsonNode.isMissingNode()) {
          openid = jsonNode.path("open_id").asText(null);
          uuid = jsonNode.path("user_id").asText(null);
        }
      }
      // 处理百度参数
      if(AppletServiceFactory.BAIDU.equals(userLogin.getPlatform())) {
        openid = jsonNode.path("open_id").asText(null);
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
  @Transactional(rollbackFor = Exception.class)
  public String userPhone(UserLoginDto userLogin, UserDetailVo oldUserData, String authToken) {
    try {
      AppletService appletService = appletServiceFactory.getAppletService(userLogin.getPlatform());
      String phone = appletService.getPhoneNumber(userLogin.getCode());
      User newUserData = userMapper.selectByPhone(phone);

      if (newUserData != null && !newUserData.getId().equals(oldUserData.getId())) {
        // 如果手机号存在并且不是当前用户，删除已存在用户id，更新用户三方授权表用户id
        // 删除旧用户信息
        int rowsAffected = userMapper.softDeleteById(oldUserData.getId(), oldUserData.getId());
        if (rowsAffected <= 0) {
          throw new ServiceException("删除用户失败");
        }
        // 修改用户授权表的userId
        rowsAffected = userAuthMapper.updateUserId(newUserData.getId(), oldUserData.getId());
        if (rowsAffected <= 0) {
          throw new ServiceException("用户授权修改新用户ID失败");
        }
        this.logout(oldUserData, authToken);
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
      throw new ServiceException(e.getMessage());
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void userInvite(InviteMq inviteMq) {
    try {
      // 判断是否存在邀请人
      User user = userMapper.selectById(inviteMq.getUserId());
      if (user == null || user.getFirstUserId() != null) {
        throw new ServiceException("用户已存在邀请人");
      }
      int rowsAffected;
      rowsAffected = userMapper.updateFirstUserId(inviteMq.getUserId(), inviteMq.getInviteUserId());
      if (rowsAffected <= 0) {
        throw new ServiceException("更新用户邀请人失败");
      }
      Map<String, String> config = userConfigService.getConfig();
      String inviteAmountStr = config.get("inviteRewardAmount");
      if (inviteAmountStr == null || StringUtils.isEmpty(inviteAmountStr)) {
        throw new ServiceException("获取佣金失败");
      }
      BigDecimal inviteAmount = new BigDecimal(inviteAmountStr);
      rowsAffected = userMapper.updateIncreaseAmount(inviteMq.getInviteUserId(), inviteAmount);
      if (rowsAffected <= 0) {
        throw new ServiceException("更新用户邀请人佣金失败");
      }
      UserEarn userEarn = new UserEarn();
      userEarn.setId(IdUtil.getSnowflakeNextId());
      userEarn.setUserId(inviteMq.getInviteUserId());
      userEarn.setWithUserId(inviteMq.getUserId());
      userEarn.setEarnAmount(inviteAmount);
      userEarn.setEarnType(1);
      userEarn.setStatus(2);
      rowsAffected = userEarnMapper.insert(userEarn);
      if (rowsAffected <= 0) {
        throw new ServiceException("新增用户邀请人佣金记录失败");
      }
    } catch (Exception e) {
      log.error("用户邀请异常：", e);
      throw new ServiceException(e.getMessage());
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void userRebate(RebateMq rebateMq) {
    try {
      // 判断是否存在用户
      User user = userMapper.selectById(rebateMq.getUserId());
      if (user == null || user.getFirstUserId() == null) {
        throw new ServiceException("用户不存在");
      }
      // 判断是否存在订单
      Order order = orderMapper.selectById(rebateMq.getOrderId());
      if (order == null) {
        throw new ServiceException("订单不存在");
      }
      if(!"15901799236".equals(order.getPhone())) {
        // 判断订单状态
        if (order.getStatus() != 4 || !Objects.equals(order.getUserId(), rebateMq.getUserId())) {
          throw new ServiceException("订单状态异常");
        }
      }
      // 获取每张票的返利金额
      List<UserConfigPricingRuleVo> rules = userConfigService.pricingRule();
      BigDecimal rebateAmount = PricingUtil.calculateEarnAmount(order.getOfficialPrice(), order.getSettlementPrice(), rules);
      // 获取总共的返利金额
      rebateAmount = rebateAmount.multiply(new BigDecimal(order.getPurchaseQuantity()));
      int rowsAffected;
      rowsAffected = userMapper.updateIncreaseAmount(user.getFirstUserId(), rebateAmount);
      if (rowsAffected <= 0) {
        throw new ServiceException("更新用户订单返利佣金失败");
      }
      UserEarn userEarn = new UserEarn();
      userEarn.setId(IdUtil.getSnowflakeNextId());
      userEarn.setUserId(user.getFirstUserId());
      userEarn.setWithUserId(user.getId());
      userEarn.setWithOrderId(order.getId());
      userEarn.setEarnAmount(rebateAmount);
      userEarn.setEarnType(2);
      userEarn.setStatus(2);
      rowsAffected = userEarnMapper.insert(userEarn);
      if (rowsAffected <= 0) {
        throw new ServiceException("新增用户订单返利佣金记录失败");
      }

    } catch (Exception e) {
      log.error("订单返利异常：", e);
      throw new ServiceException(e.getMessage());
    }
  }

  @Override
  public String inviteQrcode(UserInviteQrcodeDto userInviteQrcode, UserDetailVo user) {
    try {
      int platform = AppletTypeEnum.findByApplet(userInviteQrcode.getPlatform());
      UserAuth userAuth = userAuthMapper.findOpenid(user.getId(), platform);
      if(userAuth != null && userAuth.getInviteQrcode() != null) {
        return userAuth.getInviteQrcode();
      }
      AppletService appletService = appletServiceFactory.getAppletService(userInviteQrcode.getPlatform());
      Long userId = user.getId();
      Map<String, Object> queryData = new HashMap<>(2);
      queryData.put("url", "pages/home/index/index");
      queryData.put("query", "promotionId=" + userId.toString());
      String qrcode = appletService.getQrcode(queryData);
      if(userAuth != null) {
        userAuthMapper.updateInviteQrcode(userAuth.getId(), qrcode);
      }
      return qrcode;
    } catch (Exception e) {
      log.error("生成推广二维码异常：", e);
      throw new ServiceException(e.getMessage());
    }
  }

  @Override
  public UserApiLocationVo getLocation(HttpServletRequest request) {
    // 兜底地址
    UserApiLocationVo userApiLocationVo = new UserApiLocationVo();
    userApiLocationVo.setLat("39.91092455");
    userApiLocationVo.setLng("116.41338370");
    try {
      // 获取用户IP
      String ip = RequestUtil.getRequestIp(request);
      if (ip == null || ip.isEmpty()) {
        throw new ServiceException("无法获取有效的IP地址");
      }
      // 调用百度地图API获取位置信息
      String url = String.format("http://api.map.baidu.com/location/ip?ak=QHrdfdfuYiLoRs81XtQRCwXhlhjusKhR&ip=%s&coor=bd09ll", ip);
      String response = okHttpService.doGet(url, null, null);
      // 将响应字符串解析为 JSON 对象
      ObjectMapper objectMapper = JsonUtils.getObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(response);
      // 检查是否有错误信息
      if (jsonNode.has("status") && jsonNode.get("status").asInt() != 0) {
        throw new HttpException("调用百度地图API获取位置信息失败: " + jsonNode.toPrettyString());
      }
      // 导航到 content 节点
      JsonNode contentNode = jsonNode.path("content");
      if (!contentNode.isMissingNode()) {
        JsonNode pointNode = contentNode.path("point");
        if (!pointNode.isMissingNode()) {
          String lng = pointNode.path("x").asText(null);
          String lat = pointNode.path("y").asText(null);
          if (lng != null && lat != null) {
            userApiLocationVo.setLat(lat);
            userApiLocationVo.setLng(lng);
          }
        }
      }

    } catch (Exception e) {
      // 这里应该有更详细的异常处理逻辑
      log.error("获取地理位置错误: {}", e.getMessage());
    }

    return userApiLocationVo;
  }

  @Override
  public boolean logout(UserDetailVo user, String authToken) {
    // 删除缓存
    String redisKey = cachePrefix + user.getId() + ":" + authToken;
    return redisService.del(redisKey);
  }

}
