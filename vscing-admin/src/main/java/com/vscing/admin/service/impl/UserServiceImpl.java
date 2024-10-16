package com.vscing.admin.service.impl;

import com.vscing.admin.mapper.UserMapper;
import com.vscing.admin.service.UserService;
import com.vscing.admin.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserVo> getList() {
        return userMapper.getList();
    }
}
