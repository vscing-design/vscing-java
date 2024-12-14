package com.vscing.auth.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * VscingUserDetails
 *
 * @author vscing
 * @date 2024/12/14 22:14
 */
public interface VscingUserDetails extends UserDetails {
  Long getUserId();
}
