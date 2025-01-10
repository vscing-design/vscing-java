package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.UserService;
import com.vscing.common.service.RedisService;
import com.vscing.model.dto.UserListDto;
import com.vscing.model.entity.User;
import com.vscing.model.mapper.UserMapper;
import com.vscing.model.vo.UserListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public List<UserListVo> getList(UserListDto data, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.getList(data);
    }

    @Override
    public User getDetails(long id) {
        return userMapper.selectById(id);
    }

    @Override
    public long created(User user) {
        user.setId(IdUtil.getSnowflakeNextId());
        return userMapper.insert(user);
    }

    @Override
    public long deleted(long id, long deleterId) {
        return userMapper.softDeleteById(id, deleterId);
    }

}
