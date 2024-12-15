package com.vscing.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.admin.dto.AdminUserListDto;
import com.vscing.admin.entity.AdminUser;
import com.vscing.admin.entity.AdminUserDetails;
import com.vscing.model.mapper.AdminUserMapper;
import com.vscing.admin.service.AdminUserService;
import com.vscing.auth.service.VscingUserDetails;
import com.vscing.auth.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * AdminUserService
 *
 * @author vscing
 * @date 2024/12/14 21:08
 */

@Service
public class AdminUserServiceImpl implements AdminUserService {

  private static final Logger logger = LoggerFactory.getLogger(AdminUserServiceImpl.class);

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AdminUserMapper adminUserMapper;

  @Override
  public VscingUserDetails adminUserInfo(long id) {
    // 获取用户信息
    AdminUser adminUser = new AdminUser();
    adminUser.setId(1L);
    adminUser.setUsername("vscingAdmin");
    String encodePassword = passwordEncoder.encode("vscingAdmin@123456");
    adminUser.setPassword(encodePassword);
    adminUser.setState(1);
    // 用户关联信息 角色、菜单等
    Map<String, Object> relatedData = new HashMap<>();
    relatedData.put("role", new ArrayList<>());
    relatedData.put("menu", new ArrayList<>());

    VscingUserDetails userDetails = new AdminUserDetails(adminUser, relatedData);
    return userDetails;
  }

  @Override
  public String login(String username, String password) {
    String token = null;
    //密码需要客户端加密后传递
    try {
      // 获取用户信息
      AdminUser adminUser = new AdminUser();
      adminUser.setId(1L);
      adminUser.setUsername("vscingAdmin");
      String encodePassword = passwordEncoder.encode("vscingAdmin@123456");
      adminUser.setPassword(encodePassword);
      adminUser.setState(1);
      // 用户关联信息 角色、菜单等
      Map<String, Object> relatedData = new HashMap<>();
      relatedData.put("role", new ArrayList<>());
      relatedData.put("menu", new ArrayList<>());

      VscingUserDetails userDetails = new AdminUserDetails(adminUser, relatedData);
      if(!passwordEncoder.matches(password, userDetails.getPassword())){
        throw new BadCredentialsException("密码不正确");
      }
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      token = jwtTokenUtil.generateToken(userDetails.getUserId());
    } catch (AuthenticationException e) {
      logger.warn("登录异常:{}", e.getMessage());
    }
    return token;
  }

  @Override
  public List<AdminUser> getAdminUserList(AdminUserListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return adminUserMapper.getList(record);
  }

  @Override
  public long createAdminUser(AdminUser adminUser) {
    return 1L;
  }

}
