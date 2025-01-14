package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.RoleService;
import com.vscing.common.exception.ServiceException;
import com.vscing.model.dto.RoleListDto;
import com.vscing.model.entity.Menu;
import com.vscing.model.entity.Permission;
import com.vscing.model.entity.Role;
import com.vscing.model.mapper.MenuMapper;
import com.vscing.model.mapper.PermissionMapper;
import com.vscing.model.mapper.RoleMapper;
import com.vscing.model.request.RoleMenusRequest;
import com.vscing.model.request.RolePermissionsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Role> getList(RoleListDto record, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return roleMapper.getList(record);
    }

    @Override
    public Role getDetails(long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public long created(Role role) {
        role.setId(IdUtil.getSnowflakeNextId());
        return roleMapper.insert(role);
    }

    @Override
    public long updated(Role role) {
        return roleMapper.update(role);
    }

    @Override
    public long deleted(long id, long deleterId) {
        return roleMapper.softDeleteById(id, deleterId);
    }

    @Override
    public List<Menu> getMenuList(long id) {
        return menuMapper.getMenusByRoleId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createdMenuList(RoleMenusRequest roleMenus) {
        // 删除关联机构
        Long roleId = Long.valueOf(roleMenus.getRoleId());
        menuMapper.deleteMenusByRoleId(roleId);
        // 增加关联机构
        if(roleMenus.getMenuIds().size() > 0) {
            int rowsAffected = menuMapper.insertMenusBatch(roleId, roleMenus.getMenuIds());
            if (rowsAffected != roleMenus.getMenuIds().size()) {
                throw new ServiceException("新增关联失败");
            }
        }

        return true;
    }

    @Override
    public List<Permission> getPermissionList(long id) {
        return permissionMapper.getPermissionsByRoleId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createdPermissionList(RolePermissionsRequest rolePermissions) {
        // 删除关联机构
        Long roleId = Long.valueOf(rolePermissions.getRoleId());
        permissionMapper.deletePermissionsByRoleId(roleId);
        // 增加关联机构
        if(rolePermissions.getPermissionIds().size() > 0) {
            int rowsAffected = permissionMapper.insertPermissionsBatch(roleId, rolePermissions.getPermissionIds());
            if (rowsAffected != rolePermissions.getPermissionIds().size()) {
                throw new ServiceException("新增关联失败");
            }
        }

        return true;
    }

}
