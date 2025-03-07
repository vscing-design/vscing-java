package com.vscing.api.service;

import com.vscing.model.dto.UserEarnApiInviteDto;
import com.vscing.model.dto.UserEarnApiListDto;
import com.vscing.model.vo.UserEarnApiInviteVo;
import com.vscing.model.vo.UserEarnApiListVo;

import java.util.List;

/**
 * UserEarnService
 *
 * @author vscing
 * @date 2025/3/7 21:37
 */
public interface UserEarnService {

  /**
   * 用户收益明细列表
   */
  List<UserEarnApiListVo> getApilist(Long userId, UserEarnApiListDto queryParam, Integer pageSize, Integer pageNum);

  /**
   * 用户邀请列表
   */
  List<UserEarnApiInviteVo> getApiInvite(Long userId, UserEarnApiInviteDto queryParam, Integer pageSize, Integer pageNum);

}
