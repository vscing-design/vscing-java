package com.vscing.admin.mapper;

import com.vscing.admin.dto.UserDto;
import com.vscing.admin.dto.UserListDto;
import com.vscing.admin.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:55:08
*/
@Mapper
public interface UserMapper {

    List<UserVo> getAllList();

    int addInfo(UserDto userInfo);

    List<UserVo> getList(UserListDto queryParam, Integer pageSize, Integer pageNum);
}
