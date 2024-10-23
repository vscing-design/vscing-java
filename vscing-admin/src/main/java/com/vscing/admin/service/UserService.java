package com.vscing.admin.service;

import com.vscing.admin.dto.UserDto;
import com.vscing.admin.dto.UserListDto;
import com.vscing.admin.dto.UserSaveDto;
import com.vscing.admin.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    List<UserVo> getAllList();

    int addInfo(UserDto userInfo);

    List<UserVo> getList(UserListDto queryParam, Integer pageSize, Integer pageNum);

    UserVo getInfo(long id);

    int updateInfo(UserSaveDto userInfo);

    int deleteInfo(long id);
}
