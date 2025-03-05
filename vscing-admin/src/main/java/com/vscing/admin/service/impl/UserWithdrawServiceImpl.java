package com.vscing.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.UserWithdrawService;
import com.vscing.model.dto.UserWithdrawApproveDto;
import com.vscing.model.dto.UserWithdrawListDto;
import com.vscing.model.entity.UserWithdraw;
import com.vscing.model.mapper.UserWithdrawMapper;
import com.vscing.model.vo.UserWithdrawAmountVo;
import com.vscing.model.vo.UserWithdrawListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserWithdrawServiceImpl
 *
 * @author vscing
 * @date 2025/3/2 20:53
 */
@Service
public class UserWithdrawServiceImpl implements UserWithdrawService {

  @Autowired
  private UserWithdrawMapper userWithdrawMapper;

  @Override
  public List<UserWithdrawListVo> getList(UserWithdrawListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return userWithdrawMapper.getList(data);
  }

  @Override
  public List<UserWithdrawAmountVo> getTotalAmount() {
    return userWithdrawMapper.getUserWithdrawAmount();
  }

  @Override
  public boolean approve(UserWithdrawApproveDto data) {
    return userWithdrawMapper.approve(data) > 0;
  }
}
