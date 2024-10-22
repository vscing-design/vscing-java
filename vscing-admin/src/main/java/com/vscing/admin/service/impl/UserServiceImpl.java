package com.vscing.admin.service.impl;

import com.vscing.admin.dto.UserDto;
import com.vscing.admin.dto.UserListDto;
import com.vscing.admin.mapper.UserMapper;
import com.vscing.admin.service.UserService;
import com.vscing.admin.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserVo> getAllList() {
        return userMapper.getAllList();
    }

    @Override
    public int addInfo(UserDto userInfo) {
        return userMapper.addInfo(userInfo);
    }

    @Override
    public List<UserVo> getList(UserListDto queryParam, Integer pageSize, Integer pageNum) {
        pageNum = (pageNum - 1) * pageSize;
        return userMapper.getList(queryParam, pageSize, pageNum);
    }
}
