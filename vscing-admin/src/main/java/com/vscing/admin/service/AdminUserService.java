package com.vscing.admin.service;

import com.vscing.model.dto.AdminUserListDto;
import com.vscing.model.dto.UserListDto;
import com.vscing.model.entity.AdminUser;
import com.vscing.model.vo.UserVo;
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
