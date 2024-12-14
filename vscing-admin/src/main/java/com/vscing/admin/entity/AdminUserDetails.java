package com.vscing.admin.entity;

import com.vscing.auth.service.VscingUserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

/**
 * AdminUserDetails
 *
 * @author vscing
 * @date 2024/12/14 23:37
 */
public class AdminUserDetails implements VscingUserDetails {

  // 后台用户
  private final AdminUser adminUser;

  // 关联信息
  private final Map<String, Object> relatedData;

  public AdminUserDetails(AdminUser adminUser, Map<String, Object> relatedData) {
    this.adminUser = adminUser;
    this.relatedData = relatedData;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    //返回当前用户所拥有的资源
    return (Collection<? extends GrantedAuthority>) relatedData;
  }

  @Override
  public String getUsername() {
    return adminUser.getUsername();
  }

  @Override
  public Long getUserId() { return adminUser.getId(); }

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
