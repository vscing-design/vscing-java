package com.vscing.model.mapper;

import com.vscing.model.dto.UserListDto;
import com.vscing.model.entity.User;
import com.vscing.model.vo.UserListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:55:08
*/
@Mapper
public interface UserMapper {

    List<UserListVo> getList(UserListDto record);

    User selectById(long id);

    User selectByPhone(String phone);

    int insert(User record);

    int update(User record);

    int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

}
