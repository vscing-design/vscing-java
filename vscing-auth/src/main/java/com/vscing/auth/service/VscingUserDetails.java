package com.vscing.auth.service;

import com.vscing.model.vo.AdminUserDetailVo;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * VscingUserDetails
 *
 * @author vscing
 * @date 2024/12/14 22:14
 */
public interface VscingUserDetails extends UserDetails {
  Long getUserId();

  AdminUserDetailVo getAdminUser();
}
