package com.vscing.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.UserEarnService;
import com.vscing.model.dto.UserEarnListDto;
import com.vscing.model.mapper.UserEarnMapper;
import com.vscing.model.vo.UserEarnListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserEarnServiceImpl
 *
 * @author vscing
 * @date 2025/3/2 23:24
 */
@Service
public class UserEarnServiceImpl implements UserEarnService {

  @Autowired
  private UserEarnMapper userEarnMapper;

  @Override
  public List<UserEarnListVo> getList(UserEarnListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return userEarnMapper.getList(data);
  }

}
