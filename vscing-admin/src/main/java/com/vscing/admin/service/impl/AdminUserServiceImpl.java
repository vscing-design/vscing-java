package com.vscing.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.po.AdminUserDetails;
import com.vscing.admin.service.AdminUserService;
import com.vscing.auth.service.VscingUserDetails;
import com.vscing.auth.util.JwtTokenUtil;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.RedisService;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.common.utils.RedisKeyConstants;
import com.vscing.common.utils.RequestUtil;
import com.vscing.model.dto.AdminUserListDto;
import com.vscing.model.dto.AdminUserSaveDto;
import com.vscing.model.entity.AdminUser;
import com.vscing.model.entity.Menu;
import com.vscing.model.entity.Organization;
import com.vscing.model.entity.Permission;
import com.vscing.model.entity.Role;
import com.vscing.model.mapper.AdminUserMapper;
import com.vscing.model.mapper.MenuMapper;
import com.vscing.model.mapper.OrganizationMapper;
import com.vscing.model.mapper.PermissionMapper;
import com.vscing.model.mapper.RoleMapper;
import com.vscing.model.request.AdminUserRolesRequest;
import com.vscing.model.vo.AdminUserDetailVo;
import com.vscing.model.vo.AdminUserListVo;
import com.vscing.model.vo.AdminUserOrganizationVo;
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
import java.util.stream.Collectors;

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

  @Value("${jwt.superAdminId}")
  private Long superAdminId;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AdminUserMapper adminUserMapper;

  @Autowired
  private OrganizationMapper organizationMapper;

  @Autowired
  private RoleMapper roleMapper;

  @Autowired
  private MenuMapper menuMapper;

  @Autowired
  private PermissionMapper permissionMapper;

  @Autowired
  private RedisService redisService;

  @Override
  public VscingUserDetails adminUserInfo(long id) {
    // 获取用户信息
    AdminUserDetailVo adminUser = this.self(id);
    return new AdminUserDetails(adminUser);
  }

  @Override
  public AdminUserDetailVo self(long id) {
    // 获取用户信息
    AdminUser entity = adminUserMapper.selectById(id);
    // 直接调用改进后的 Mapper 方法进行转换
    AdminUserDetailVo adminUser = MapstructUtils.convert(entity, AdminUserDetailVo.class);
    if (adminUser != null && adminUser.getId().equals(superAdminId)) {
      // 用户关联机构
      List<Organization> orgList = organizationMapper.getList(null);
      adminUser.setRelatedOrgList(orgList);
      // 用户关联角色
      List<Role> roleList = roleMapper.getList(null);
      adminUser.setRelatedRoleList(roleList);
      // 用户关联菜单（基于角色ID）
      List<Menu> menuList = menuMapper.getList(null);
      adminUser.setRelatedMenuList(menuList);
      // 用户关联按钮权限（基于角色ID）
      List<Permission> permissionList = permissionMapper.getList(null);
      adminUser.setRelatedPermissionList(permissionList);
    } else if (adminUser != null) {
      // 用户关联机构
      List<Organization> orgList = organizationMapper.getOrganizationsByUserId(adminUser.getId());
      adminUser.setRelatedOrgList(orgList);
      // 用户关联角色
      List<Role> roleList = roleMapper.getRolesByAdminUserId(adminUser.getId());
      adminUser.setRelatedRoleList(roleList);
      // 用户关联菜单（基于角色ID）
      if (!roleList.isEmpty()) {
        List<Long> roleIds = roleList.stream().map(Role::getId).collect(Collectors.toList());
        List<Menu> menuList = menuMapper.getMenusByRoleIds(roleIds);
        adminUser.setRelatedMenuList(menuList);
      }
      // 用户关联菜单（基于角色ID）
      if (!roleList.isEmpty()) {
        List<Long> roleIds = roleList.stream().map(Role::getId).collect(Collectors.toList());
        List<Permission> permissionList = permissionMapper.getPermissionsByRoleIds(roleIds);
        adminUser.setRelatedPermissionList(permissionList);
      }
    }

    return adminUser;
  }

  @Override
  public String login(String username, String password, HttpServletRequest request) {
    String token = null;
    //密码需要客户端加密后传递
    try {
      // 获取用户信息
      AdminUser entity = adminUserMapper.selectByUsername(username);
      if(entity != null && !passwordEncoder.matches(password, entity.getPassword())){
        throw new BadCredentialsException("密码不正确");
      }
      // 获取用户信息
      AdminUserDetailVo adminUser = this.self(entity.getId());
      VscingUserDetails userDetails = new AdminUserDetails(adminUser);
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
  public boolean logout(AdminUserDetailVo adminUser, String authToken) {
    // 删除缓存
    String redisKey = RedisKeyConstants.CACHE_KEY_PREFIX_ADMIN + adminUser.getId() + RedisKeyConstants.KEY_SEPARATOR + authToken;
    return redisService.del(redisKey);
  }

  @Override
  public List<AdminUserListVo> getList(AdminUserListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);

    // 查询用户列表
    List<AdminUserListVo> adminUserList = adminUserMapper.getList(record);

    // 提取用户 ID 列表
    List<Long> adminUserIds = adminUserList.stream().map(AdminUserListVo::getId).collect(Collectors.toList());

    // 空数据处理
    if (CollUtil.isEmpty((adminUserIds))) {
      return adminUserList;
    }

    // 根据用户 ID 列表查询机构信息
    List<AdminUserOrganizationVo> organizationList = organizationMapper.getOrganizationsByUserIds(adminUserIds);

    // 将机构信息按用户 ID 分组
    Map<Long, List<AdminUserOrganizationVo>> organizationMap = organizationList.stream()
        .collect(Collectors.groupingBy(AdminUserOrganizationVo::getAdminUserId));

    // 给每个用户增加机构列表数据
    for (AdminUserListVo adminUser : adminUserList) {
      adminUser.setOrganizationList(organizationMap.getOrDefault(adminUser.getId(), List.of()));
    }

    return adminUserList;
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
