package com.vscing.admin.service;

import com.vscing.model.dto.RoleListDto;
import com.vscing.model.entity.Role;

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
    
}