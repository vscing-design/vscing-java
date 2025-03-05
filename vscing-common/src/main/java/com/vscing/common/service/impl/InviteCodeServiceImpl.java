package com.vscing.common.service.impl;

import com.vscing.common.service.InviteCodeService;
import com.vscing.common.utils.EncryptUtils;
import org.springframework.stereotype.Service;

@Service
public class InviteCodeServiceImpl implements InviteCodeService {

  // 默认密码
  private static final String DEFAULT_PASSWORD = "HY88888666699999";

  @Override
  public String idToInviteCode(Long id) {
    try {
      // 对ID进行加密
      return EncryptUtils.encryptByAes(String.valueOf(id), DEFAULT_PASSWORD);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Long inviteCodeToId(String inviteCode) {
    try {
      String decodedStr = EncryptUtils.decryptByAes(inviteCode, DEFAULT_PASSWORD);
      return Long.parseLong(decodedStr);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
