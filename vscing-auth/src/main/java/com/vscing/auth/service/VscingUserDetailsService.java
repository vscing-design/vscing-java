package com.vscing.auth.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * VscingUserDetailsService
 *
 * @author vscing
 * @date 2024/12/14 21:10
 */
public interface VscingUserDetailsService {

  /**
   * 扩展用户id查询
  */
  VscingUserDetails loadUserByUserId(Long id) throws UsernameNotFoundException;
}
