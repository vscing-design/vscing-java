package com.vscing.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.admin.dto.UserDto;
import com.vscing.admin.dto.UserListDto;
import com.vscing.admin.dto.UserSaveDto;
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
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.getList(queryParam);
    }

    @Override
    public UserVo getInfo(long id) {
        return userMapper.getInfo(id);
    }

    @Override
    public int updateInfo(UserSaveDto userInfo) {
        return userMapper.updateInfo(userInfo);
    }

    @Override
    public int deleteInfo(long id) {
        return userMapper.deleteInfo(id);
    }
}
