package com.vscing.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:55:08
*/
@Mapper
public interface UserMapper {

  List<UserVo> getList();
}