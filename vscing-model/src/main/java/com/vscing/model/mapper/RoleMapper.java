package com.vscing.model.mapper;

import com.vscing.model.dto.RoleListDto;
import com.vscing.model.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RoleMapper
 *
 * @author vscing
 * @date 2024/12/22 00:48
 */
@Mapper
public interface RoleMapper {

  List<Role> getList(@Param("record") RoleListDto record);

  Role selectById(long id);

  int insert(Role record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(Role record);

  List<Role> getRolesByUserId(long id);

}
