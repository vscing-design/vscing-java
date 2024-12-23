package com.vscing.model.mapper;

import com.vscing.model.dto.MenuListDto;
import com.vscing.model.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MenuMapper
 *
 * @author vscing
 * @date 2024/12/22 00:44
 */
@Mapper
public interface MenuMapper {

  List<Menu> getList(@Param("record") MenuListDto record);

  Menu selectById(long id);

  int insert(Menu record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(Menu record);

  List<Menu> getMenusByRoleId(long id);

  int deleteMenusByRoleId(long id);

  int insertMenusBatch(@Param("id") long id, @Param("menuIds") List<Long> menuIds);

}
