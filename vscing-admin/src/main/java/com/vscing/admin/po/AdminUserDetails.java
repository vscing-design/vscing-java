package com.vscing.admin.po;

import com.vscing.auth.service.VscingUserDetails;
import com.vscing.model.vo.AdminUserDetailVo;

/**
 * AdminUserDetails
 *
 * @author vscing
 * @date 2024/12/14 23:37
 */
public interface AdminUserDetails extends VscingUserDetails {

  AdminUserDetailVo getAdminUser();

}
