package com.vscing.api.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.api.service.UserWithdrawService;
import com.vscing.model.dto.UserWithdrawApiListDto;
import com.vscing.model.entity.UserWithdraw;
import com.vscing.model.enums.AppletTypeEnum;
import com.vscing.model.mapper.UserWithdrawMapper;
import com.vscing.model.request.InitiateWithdrawRequest;
import com.vscing.model.vo.UserWithdrawApiListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWithdrawServiceImpl implements UserWithdrawService {

  @Autowired
  private UserWithdrawMapper userWithdrawMapper;

  @Override
  public List<UserWithdrawApiListVo> getApilist(Long userId, UserWithdrawApiListDto queryParam, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return userWithdrawMapper.selectApiList(userId, queryParam);
  }

  @Override
  public boolean initiateApiWithdraw(Long userId, InitiateWithdrawRequest initiateWithdrawRequest) {
    UserWithdraw userWithdraw = new UserWithdraw();
    userWithdraw.setId(IdUtil.getSnowflakeNextId());
    userWithdraw.setUserId(userId);
    userWithdraw.setPlatform(AppletTypeEnum.findByApplet(initiateWithdrawRequest.getPlatform()));
    userWithdraw.setWithdrawAmount(initiateWithdrawRequest.getWithdrawAmount());
    return userWithdrawMapper.insertInitiateWithdraw(userWithdraw) > 0;
  }
}
