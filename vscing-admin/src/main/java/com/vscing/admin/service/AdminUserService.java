package com.vscing.admin.service;

import com.vscing.model.dto.AdminUserListDto;
import com.vscing.model.entity.AdminUser;
import com.vscing.auth.service.VscingUserDetails;
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
  long created(AdminUser adminUser);

  /**
   * 编辑
   */
  long updated(AdminUser adminUser);

  /**
   * 删除
   */
  long deleted(long id, long deleterId);



}
