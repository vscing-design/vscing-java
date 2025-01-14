package com.vscing.admin.service;

import com.vscing.model.dto.RoleListDto;
import com.vscing.model.entity.Menu;
import com.vscing.model.entity.Permission;
import com.vscing.model.entity.Role;
import com.vscing.model.request.RoleMenusRequest;
import com.vscing.model.request.RolePermissionsRequest;

import java.util.List;

public interface RoleService {

    /**
     * 列表
     */
    List<Role> getList(RoleListDto record, Integer pageSize, Integer pageNum);

    /**
     * 详情
     */
    Role getDetails(long id);

    /**
     * 新增
     */
    long created(Role Role);

    /**
     * 编辑
     */
    long updated(Role Role);

    /**
     * 删除
     */
    long deleted(long id, long deleterId);

    /**
     * 角色关联菜单列表
     */
    List<Menu> getMenuList(long id);

    /**
     * 角色关联菜单
     */
    boolean createdMenuList(RoleMenusRequest roleMenus);

    /**
     * 角色关联按钮列表
     */
    List<Permission> getPermissionList(long id);

    /**
     * 角色关联按钮
     */
    boolean createdPermissionList(RolePermissionsRequest rolePermissions);
    
}
