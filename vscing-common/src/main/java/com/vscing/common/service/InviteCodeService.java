package com.vscing.common.service;

/**
 * OkHttpService
 *
 * @author vscing
 * @date 2025/1/8 11:31
 */
public interface InviteCodeService {

  /**
   * toSerialCode 用户ID生成邀请码
   * @param id 用户ID
   */
  String idToInviteCode(Long id);

  /**
   * inviteCodeToId 邀请码转用户ID
   * @param inviteCode 邀请码
   */
  Long inviteCodeToId(String inviteCode);

}
