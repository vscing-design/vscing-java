package com.vscing.admin.service;

import com.vscing.auth.service.VscingUserDetails;
import com.vscing.model.dto.AdminUserListDto;
import com.vscing.model.dto.AdminUserSaveDto;
import com.vscing.model.entity.AdminUser;
import com.vscing.model.entity.Role;
import com.vscing.model.request.AdminUserRolesRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * AdminUserService
 *
 * @author vscing
 * @date 2024/12/14 21:08
 */
public interface AdminUserService {

  /**
   * 搭配springSecurity权限
  */
  VscingUserDetails adminUserInfo(long id);

  /**
   * 登录后获取token
   */
  String login(String username, String password, HttpServletRequest request);

  /**
   * 列表
  */
  List<AdminUser> getList(AdminUserListDto record, Integer pageSize, Integer pageNum);

  /**
   * 详情
   */
  AdminUser getDetails(long id);

  /**
   * 新增
  */
  boolean created(AdminUserSaveDto adminUser);

  /**
   * 编辑
   */
  boolean updated(AdminUserSaveDto adminUser);

  /**
   * 更新机构信息
   */
  void updateOrganizations(Long id, List<Long> organizationIds);

  /**
   * 删除
   */
  boolean deleted(long id, long deleterId);

  /**
   * 用户关联角色列表
   */
  List<Role> getRoleList(long id);

  /**
   * 用户关联角色
   */
  boolean createdRoleList(AdminUserRolesRequest adminUserRoles);



}
