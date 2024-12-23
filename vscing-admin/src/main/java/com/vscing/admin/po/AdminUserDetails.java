package com.vscing.admin.po;

import com.vscing.auth.service.VscingUserDetails;
import com.vscing.model.vo.AdminUserDetailVo;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * AdminUserDetails
 *
 * @author vscing
 * @date 2024/12/14 23:37
 */
public class AdminUserDetails implements VscingUserDetails {

  // 后台用户
  private final AdminUserDetailVo adminUser;

  public AdminUserDetails(AdminUserDetailVo adminUser) {
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
