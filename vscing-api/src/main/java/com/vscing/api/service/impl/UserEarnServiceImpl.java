package com.vscing.api.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.api.service.UserEarnService;
import com.vscing.model.dto.UserEarnApiInviteDto;
import com.vscing.model.dto.UserEarnApiListDto;
import com.vscing.model.mapper.UserEarnMapper;
import com.vscing.model.vo.UserEarnApiInviteVo;
import com.vscing.model.vo.UserEarnApiListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserEarnServiceImpl
 *
 * @author vscing
 * @date 2025/3/7 21:54
 */
@Service
public class UserEarnServiceImpl implements UserEarnService {

  @Autowired
  private UserEarnMapper userEarnMapper;

  @Override
  public List<UserEarnApiListVo> getApilist(Long userId, UserEarnApiListDto queryParam, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return userEarnMapper.selectApiList(userId, queryParam);
  }

  @Override
  public List<UserEarnApiInviteVo> getApiInvite(Long userId, UserEarnApiInviteDto queryParam, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return userEarnMapper.selectApiInvite(userId, queryParam);
  }
}
