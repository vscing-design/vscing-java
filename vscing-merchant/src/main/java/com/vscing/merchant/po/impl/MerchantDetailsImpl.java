package com.vscing.merchant.po.impl;

import com.vscing.merchant.po.MerchantDetails;
import com.vscing.model.vo.MerchantDetailVo;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * UserDetailsImpl
 *
 * @author vscing
 * @date 2025/1/6 12:13
 */
public class MerchantDetailsImpl implements MerchantDetails {

  // 后台用户
  private final MerchantDetailVo merchantDetail;

  public MerchantDetailsImpl(MerchantDetailVo merchantDetail) {
    this.merchantDetail = merchantDetail;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    //返回当前用户所拥有的资源
    return null;
  }

  @Override
  public String getUsername() {
    return merchantDetail.getMerchantName();
  }

  @Override
  public Long getUserId() { return merchantDetail.getId(); }

  @Override
  public MerchantDetailVo getMerchant() { return merchantDetail; }

  @Override
  public String getPassword() {
    return merchantDetail.getPassword();
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
    return merchantDetail.getStatus().equals(1);
  }

}
