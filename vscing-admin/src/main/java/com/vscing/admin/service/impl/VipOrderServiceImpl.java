package com.vscing.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.VipOrderService;
import com.vscing.model.dto.AdminVipOrderDto;
import com.vscing.model.mapper.VipOrderMapper;
import com.vscing.model.vo.AdminVipOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * VipOrderServiceImpl
 *
 * @author vscing
 * @date 2025/6/2 17:39
 */
@Service
public class VipOrderServiceImpl implements VipOrderService {

  @Autowired
  private VipOrderMapper vipOrderMapper;

  @Override
  public List<AdminVipOrderVo> getList(AdminVipOrderDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return vipOrderMapper.getAdminList(record);
  }
}
