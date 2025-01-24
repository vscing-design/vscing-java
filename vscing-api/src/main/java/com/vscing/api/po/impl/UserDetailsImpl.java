package com.vscing.api.po.impl;

import com.vscing.api.po.UserDetails;
import com.vscing.model.vo.UserDetailVo;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * UserDetailsImpl
 *
 * @author vscing
 * @date 2025/1/6 12:13
 */
public class UserDetailsImpl implements UserDetails {

  // 后台用户
  private final UserDetailVo userDetail;

  public UserDetailsImpl(UserDetailVo userDetail) {
    this.userDetail = userDetail;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    //返回当前用户所拥有的资源
    return null;
  }

  @Override
  public String getUsername() {
    return userDetail.getUsername();
  }

  @Override
  public Long getUserId() { return userDetail.getId(); }

  @Override
  public UserDetailVo getUser() { return userDetail; }

  @Override
  public String getPassword() {
    return userDetail.getPassword();
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
    return userDetail.getStatus().equals(1);
  }

}
