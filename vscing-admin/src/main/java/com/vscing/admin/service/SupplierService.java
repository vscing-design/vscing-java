package com.vscing.admin.service;

import com.vscing.model.dto.AdminUserListDto;
import com.vscing.model.dto.SupplierListDto;
import com.vscing.model.entity.AdminUser;
import com.vscing.model.entity.Supplier;

import java.util.List;

/**
 * SupplierService
 *
 * @author vscing
 * @date 2024/12/20 00:42
 */
public interface SupplierService {

  /**
   * 列表
   */
  List<Supplier> getList(SupplierListDto data, Integer pageSize, Integer pageNum);

  /**
   * 详情
   */
  Supplier getDetails(long id);

  /**
   * 新增
   */
  long created(Supplier data);

  /**
   * 编辑
   */
  long updated(Supplier data);

  /**
   * 删除
   */
  long deleted(long id, long deleterId);

}
