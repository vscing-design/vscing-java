package com.vscing.model.mapper;

import com.vscing.model.dto.PermissionListDto;
import com.vscing.model.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PermissionMapper
 *
 * @author vscing
 * @date 2024/12/24 23:54
 */
@Mapper
public interface PermissionMapper {

  List<Permission> getList(PermissionListDto record);

  Permission selectById(long id);

  int insert(Permission record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(Permission record);

  List<Permission> getPermissionsByRoleId(long id);

  List<Permission> getPermissionsByRoleIds(List<Long> ids);

  int deletePermissionsByRoleId(long id);

  int insertPermissionsBatch(@Param("id") long id, @Param("permissionIds") List<Long> permissionIds);

}
