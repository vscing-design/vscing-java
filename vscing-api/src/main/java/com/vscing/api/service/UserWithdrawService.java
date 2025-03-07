package com.vscing.api.service;

import com.vscing.model.dto.UserWithdrawApiListDto;
import com.vscing.model.request.InitiateWithdrawRequest;
import com.vscing.model.vo.UserWithdrawApiListVo;

import java.util.List;

/**
 * UserWithdrawService
 *
 * @author vscing
 * @date 2025/1/6 11:47
 */
public interface UserWithdrawService {

  /**
   * 用户提现列表
   */
  List<UserWithdrawApiListVo> getApilist(Long userId, UserWithdrawApiListDto queryParam, Integer pageSize, Integer pageNum);

  /**
   * 用户发起提现
   */
  boolean initiateApiWithdraw(Long userId, InitiateWithdrawRequest initiateWithdrawRequest);


}
