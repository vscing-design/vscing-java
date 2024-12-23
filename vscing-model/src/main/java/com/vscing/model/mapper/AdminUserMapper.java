package com.vscing.model.mapper;

import com.vscing.model.dto.AdminUserListDto;
import com.vscing.model.dto.AdminUserSaveDto;
import com.vscing.model.entity.AdminUser;
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

  int insert(AdminUserSaveDto record);

  int deleteById(long id);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(AdminUserSaveDto record);

}
