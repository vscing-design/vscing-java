package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.RoleService;
import com.vscing.common.exception.ServiceException;
import com.vscing.model.dto.RoleListDto;
import com.vscing.model.entity.Menu;
import com.vscing.model.entity.Role;
import com.vscing.model.mapper.MenuMapper;
import com.vscing.model.mapper.RoleMapper;
import com.vscing.model.request.RoleMenusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

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
//    @Transactional(rollbackFor = Exception.class)
    public boolean createdMenuList(RoleMenusRequest roleMenus) {
        // 删除关联机构
        menuMapper.deleteMenusByRoleId(roleMenus.getRoleId());
        // 增加关联机构
        if(roleMenus.getMenuIds().size() > 0) {
            int rowsAffected = menuMapper.insertMenusBatch(roleMenus.getRoleId(), roleMenus.getMenuIds());
            if (rowsAffected != roleMenus.getMenuIds().size()) {
                throw new ServiceException("新增关联失败");
            }
        }

        return true;
    }
}
