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

  private String maskMiddleDigits(String phoneNumber) {
    if (phoneNumber == null || phoneNumber.length() != 11) {
      throw new IllegalArgumentException("无效的手机号码");
    }
    // 确保手机号长度为11位
    return phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7);
  }

  @Override
  public List<UserEarnApiListVo> getApilist(Long userId, UserEarnApiListDto queryParam, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    List<UserEarnApiListVo> list = userEarnMapper.selectApiList(userId, queryParam);
    list.forEach(item -> {
      item.setWithQuantity(item.getWithQuantity() == null ? 0 : item.getWithQuantity());
      item.setWithPhone(item.getWithPhone() == null ? "" : maskMiddleDigits(item.getWithPhone()));
    });
    return list;
  }

  @Override
  public List<UserEarnApiInviteVo> getApiInvite(Long userId, UserEarnApiInviteDto queryParam, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    List<UserEarnApiInviteVo> list = userEarnMapper.selectApiInvite(userId, queryParam);
    list.forEach(item -> {
      item.setWithPhone(item.getWithPhone() == null ? "" : maskMiddleDigits(item.getWithPhone()));
    });
    return list;
  }
}
