package com.vscing.api.service.impl;

import com.vscing.api.service.UserAuthService;
import com.vscing.model.mapper.UserAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserAuthServiceImpl
 *
 * @author vscing
 * @date 2025/3/9 21:05
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {

  @Autowired
  private UserAuthMapper userAuthMapper;

}
