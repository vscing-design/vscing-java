package com.vscing.admin.service;

import com.vscing.model.dto.PermissionListDto;
import com.vscing.model.entity.Permission;

import java.util.List;

/**
 * PermissionService
 *
 * @author vscing
 * @date 2024/12/25 00:10
 */
public interface PermissionService {

  /**
   * 列表
   */
  List<Permission> getList(PermissionListDto record, Integer pageSize, Integer pageNum);

  /**
   * 详情
   */
  Permission getDetails(long id);

  /**
   * 新增
   */
  long created(Permission permission);

  /**
   * 编辑
   */
  long updated(Permission permission);

  /**
   * 删除
   */
  long deleted(long id, long deleterId);
  
}
