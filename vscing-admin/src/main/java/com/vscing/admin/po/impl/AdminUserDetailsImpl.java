package com.vscing.admin.po.impl;

import com.vscing.admin.po.AdminUserDetails;
import com.vscing.model.vo.AdminUserDetailVo;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * AdminUserDetailsImpl
 *
 * @author vscing
 * @date 2025/1/6 12:10
 */
public class AdminUserDetailsImpl implements AdminUserDetails {

  /**
   * 后台用户信息
  */
  private final AdminUserDetailVo adminUser;

  public AdminUserDetailsImpl(AdminUserDetailVo adminUser) {
    this.adminUser = adminUser;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    //返回当前用户所拥有的资源
    return null;
  }

  @Override
  public String getUsername() {
    return adminUser.getUsername();
  }

  @Override
  public Long getUserId() { return adminUser.getId(); }

  @Override
  public AdminUserDetailVo getAdminUser() { return adminUser; }

  @Override
  public String getPassword() {
    return adminUser.getPassword();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return adminUser.getStatus().equals(1);
  }
}
