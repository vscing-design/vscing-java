package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.PermissionService;
import com.vscing.model.dto.PermissionListDto;
import com.vscing.model.entity.Permission;
import com.vscing.model.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PermissionServiceImpl
 *
 * @author vscing
 * @date 2024/12/25 00:11
 */
@Service
public class PermissionServiceImpl implements PermissionService {

  @Autowired
  private PermissionMapper permissionMapper;

  @Override
  public List<Permission> getList(PermissionListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return permissionMapper.getList(record);
  }

  @Override
  public Permission getDetails(long id) {
    return permissionMapper.selectById(id);
  }

  @Override
  public long created(Permission permission) {
    permission.setId(IdUtil.getSnowflakeNextId());
    return permissionMapper.insert(permission);
  }

  @Override
  public long updated(Permission permission) {
    return permissionMapper.update(permission);
  }

  @Override
  public long deleted(long id, long deleterId) {
    return permissionMapper.softDeleteById(id, deleterId);
  }
  
}
