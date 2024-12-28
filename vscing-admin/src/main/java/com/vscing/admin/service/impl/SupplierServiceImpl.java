package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.SupplierService;
import com.vscing.common.service.RedisService;
import com.vscing.model.dto.SupplierListDto;
import com.vscing.model.entity.Supplier;
import com.vscing.model.mapper.SupplierMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AdminUserService
 *
 * @author vscing
 * @date 2024/12/14 21:08
 */
@Slf4j
@Service
public class SupplierServiceImpl implements SupplierService {

  @Autowired
  private SupplierMapper mapper;

  @Autowired
  private RedisService redisService;

  @Override
  public List<Supplier> getList(SupplierListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return mapper.getList(data);
  }

  @Override
  public Supplier getDetails(long id) {
    return mapper.selectById(id);
  }

  @Override
  public long created(Supplier supplier) {
    supplier.setId(IdUtil.getSnowflakeNextId());
    return mapper.insert(supplier);
  }

  @Override
  public long updated(Supplier supplier) {
    return mapper.update(supplier);
  }

  @Override
  public long deleted(long id, long deleterId) {
    return mapper.softDeleteById(id, deleterId);
  }

}
