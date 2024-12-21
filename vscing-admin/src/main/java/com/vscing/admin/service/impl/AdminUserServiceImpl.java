package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.common.service.RedisService;
import com.vscing.common.util.RedisKeyConstants;
import com.vscing.common.util.RequestUtil;
import com.vscing.model.dto.AdminUserListDto;
import com.vscing.model.entity.AdminUser;
import com.vscing.admin.po.AdminUserDetails;
import com.vscing.model.mapper.AdminUserMapper;
import com.vscing.admin.service.AdminUserService;
import com.vscing.auth.service.VscingUserDetails;
import com.vscing.auth.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

/**
 * AdminUserService
 *
 * @author vscing
 * @date 2024/12/14 21:08
 */

@Service
public class AdminUserServiceImpl implements AdminUserService {

  private static final Logger logger = LoggerFactory.getLogger(AdminUserServiceImpl.class);

  @Value("${jwt.expiration}")
  private Long expiration;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AdminUserMapper adminUserMapper;

  @Autowired
  private RedisService redisService;

  @Override
  public VscingUserDetails adminUserInfo(long id) {
    // 获取用户信息
    AdminUser adminUser = adminUserMapper.selectById(id);
    // 用户关联信息 角色、菜单等
    Map<String, Object> relatedData = new HashMap<>(2);
    relatedData.put("role", new ArrayList<>());
    relatedData.put("menu", new ArrayList<>());

    VscingUserDetails userDetails = new AdminUserDetails(adminUser, relatedData);
    return userDetails;
  }

  @Override
  public String login(String username, String password, HttpServletRequest request) {
    String token = null;
    //密码需要客户端加密后传递
    try {
      // 获取用户信息
      AdminUser adminUser = adminUserMapper.selectByUsername(username);
      if(adminUser != null && !passwordEncoder.matches(password, adminUser.getPassword())){
        throw new BadCredentialsException("密码不正确");
      }
      // 用户关联信息 角色、菜单等
      Map<String, Object> relatedData = new HashMap<>(2);
      relatedData.put("role", new ArrayList<>());
      relatedData.put("menu", new ArrayList<>());
      // 用户信息上下文
      VscingUserDetails userDetails = new AdminUserDetails(adminUser, relatedData);
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      token = jwtTokenUtil.generateToken(userDetails.getUserId());

      // 更新最后登录信息
      adminUser.setLastIp(RequestUtil.getRequestIp(request));
      adminUser.setLoginAt(LocalDateTime.now());

      // 记录到redis
      redisService.set(RedisKeyConstants.CACHE_KEY_PREFIX_ADMIN + adminUser.getId() + RedisKeyConstants.KEY_SEPARATOR + token, adminUser, expiration);

      // 更新表数据
      adminUserMapper.update(adminUser);
    } catch (AuthenticationException e) {
      logger.warn("登录异常:{}", e.getMessage());
    }
    return token;
  }

  @Override
  public List<AdminUser> getList(AdminUserListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return adminUserMapper.getList(record);
  }

  @Override
  public AdminUser getDetails(long id) {
    // 获取用户信息
    AdminUser adminUser = adminUserMapper.selectById(id);
    // 用户关联信息 角色、菜单等
    Map<String, Object> relatedData = new HashMap<>(2);
    relatedData.put("role", new ArrayList<>());
    relatedData.put("menu", new ArrayList<>());
    return adminUser;
  }

  @Override
  public long created(AdminUser adminUser) {
    String encodePassword = passwordEncoder.encode(adminUser.getPassword());
    adminUser.setPassword(encodePassword);
    adminUser.setId(IdUtil.getSnowflakeNextId());
    return adminUserMapper.insert(adminUser);
  }

  @Override
  public long updated(AdminUser adminUser) {
    return adminUserMapper.update(adminUser);
  }

  @Override
  public long deleted(long id, long deleterId) {
    return adminUserMapper.softDeleteById(id, deleterId);
  }

}
