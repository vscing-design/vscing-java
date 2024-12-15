package com.vscing.model.mapper;

import com.vscing.admin.dto.AdminUserListDto;
import com.vscing.admin.dto.UserDto;
import com.vscing.admin.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AdminUserMapper
 *
 * @author vscing
 * @date 2024/12/15 22:05
 */
@Mapper
public interface AdminUserMapper {

  List<AdminUser> getList(@Param("record") AdminUserListDto record);

  AdminUser selectById(long id);

  AdminUser selectByUsername(String username);

  int insert(AdminUser record);

  int deleteById(long id);

  int softDeleteById(long id);

  int update(AdminUser record);

}
