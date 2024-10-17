package com.vscing.admin.service;

import com.vscing.admin.dto.UserDto;
import com.vscing.admin.vo.UserVo;

import java.util.List;

public interface UserService {

    List<UserVo> getList();

    int addInfo(UserDto userInfo);
}

