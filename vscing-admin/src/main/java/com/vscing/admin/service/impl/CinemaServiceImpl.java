package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.CinemaService;
import com.vscing.model.dto.CinemaListDto;
import com.vscing.model.entity.Cinema;
import com.vscing.model.mapper.CinemaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CinemaServiceImpl
 *
 * @author vscing
 * @date 2024/12/26 22:19
 */
@Service
public class CinemaServiceImpl implements CinemaService {

  @Autowired
  private CinemaMapper mapper;

  @Override
  public List<Cinema> getList(CinemaListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return mapper.getList(data);
  }

  @Override
  public Cinema getDetails(long id) {
    return mapper.selectById(id);
  }

  @Override
  public long created(Cinema supplier) {
    supplier.setId(IdUtil.getSnowflakeNextId());
    return mapper.insert(supplier);
  }

  @Override
  public long updated(Cinema supplier) {
    return mapper.update(supplier);
  }

  @Override
  public long deleted(long id, long deleterId) {
    return mapper.softDeleteById(id, deleterId);
  }
  
}
