package com.vscing.admin.mapper;

import com.vscing.admin.dto.UserDto;
import com.vscing.admin.dto.UserListDto;
import com.vscing.admin.dto.UserSaveDto;
import com.vscing.admin.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:55:08
*/
@Mapper
public interface UserMapper {

    List<UserVo> getList(@Param("queryParam")UserListDto queryParam);

    List<UserVo> getAllList();

    int addInfo(UserDto userInfo);

    // 指定了parameterType 就不需要@Param("id")
//    UserVo getInfo(@Param("id") Long id);

    UserVo getInfo(long id);

    int updateInfo(@Param("userInfo")UserSaveDto userInfo);

    int deleteInfo(long id);

}
