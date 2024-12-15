package com.vscing.admin.service;

import com.vscing.admin.dto.AdminUserListDto;
import com.vscing.admin.dto.UserListDto;
import com.vscing.admin.entity.AdminUser;
import com.vscing.admin.vo.UserVo;
import com.vscing.auth.service.VscingUserDetails;

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
  String login(String username, String password);

  /**
   * 管理员列表
  */
  List<AdminUser> getAdminUserList(AdminUserListDto record, Integer pageSize, Integer pageNum);

  /**
   * 新增管理员
  */
  long createAdminUser(AdminUser adminUser);

}
