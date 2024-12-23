package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.po.AdminUserDetails;
import com.vscing.admin.service.AdminUserService;
import com.vscing.auth.service.VscingUserDetails;
import com.vscing.auth.util.JwtTokenUtil;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.RedisService;
import com.vscing.common.util.MapstructUtils;
import com.vscing.common.util.RedisKeyConstants;
import com.vscing.common.util.RequestUtil;
import com.vscing.model.dto.AdminUserListDto;
import com.vscing.model.dto.AdminUserSaveDto;
import com.vscing.model.entity.AdminUser;
import com.vscing.model.entity.Role;
import com.vscing.model.mapper.AdminUserMapper;
import com.vscing.model.mapper.OrganizationMapper;
import com.vscing.model.mapper.RoleMapper;
import com.vscing.model.request.AdminUserRolesRequest;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

  @Value("${jwt.expiration}")
  private Long expiration;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AdminUserMapper adminUserMapper;

  @Autowired
  private RoleMapper roleMapper;

  @Autowired
  private OrganizationMapper organizationMapper;

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

      AdminUserSaveDto adminUserDto = MapstructUtils.convert(adminUser, AdminUserSaveDto.class);
      // 更新表数据
      adminUserMapper.update(adminUserDto);
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
  @Transactional(rollbackFor = Exception.class)
  public boolean created(AdminUserSaveDto adminUser) {
    try {
      String encodePassword = passwordEncoder.encode(adminUser.getPassword());
      Long id = IdUtil.getSnowflakeNextId();
      adminUser.setPassword(encodePassword);
      adminUser.setId(id);
      // 创建用户
      int rowsAffected = adminUserMapper.insert(adminUser);
      if (rowsAffected <= 0) {
        throw new ServiceException("新增用户失败");
      }
      // 增加关联机构
      rowsAffected = organizationMapper.insertOrganizationsBatch(id, adminUser.getOrganizationIds());
      if (rowsAffected != adminUser.getOrganizationIds().size()) {
        throw new ServiceException("新增关联机构失败");
      }
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
    return true;
  }

  @Override
  public boolean updated(AdminUserSaveDto adminUser) {
    // 编辑用户
    adminUserMapper.update(adminUser);
    // 关联机构
    updateOrganizations(adminUser.getId(), adminUser.getOrganizationIds());
    return true;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateOrganizations(Long id, List<Long> organizationIds) {
    // 删除关联机构
    int rowsAffected = organizationMapper.deleteOrganizationsByUserId(id);
    if (rowsAffected <= 0) {
      throw new ServiceException("删除关联用户失败");
    }
    // 增加关联机构
    rowsAffected = organizationMapper.insertOrganizationsBatch(id, organizationIds);
    if (rowsAffected != organizationIds.size()) {
      throw new ServiceException("新增关联机构失败");
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleted(long id, long deleterId) {
    // 创建用户
    int rowsAffected = adminUserMapper.softDeleteById(id, deleterId);
    if (rowsAffected <= 0) {
      throw new ServiceException("删除用户失败");
    }
    // 删除关联机构
    rowsAffected = organizationMapper.deleteOrganizationsByUserId(id);
    if (rowsAffected <= 0) {
      throw new ServiceException("删除关联用户失败");
    }
    return true;
  }

  @Override
  public List<Role> getRoleList(long id) {
    return roleMapper.getRolesByAdminUserId(id);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean createdRoleList(AdminUserRolesRequest adminUserRoles) {
    // 删除关联
    roleMapper.deleteRolesByAdminUserId(adminUserRoles.getAdminUserId());
    // 增加关联
    if(adminUserRoles.getRoleIds().size() > 0) {
      int rowsAffected = roleMapper.insertRolesBatch(adminUserRoles.getAdminUserId(), adminUserRoles.getRoleIds());
      if (rowsAffected != adminUserRoles.getRoleIds().size()) {
        throw new ServiceException("新增关联失败");
      }
    }

    return true;
  }

}
