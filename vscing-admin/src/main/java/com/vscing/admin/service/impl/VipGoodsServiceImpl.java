package com.vscing.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.VipGoodsService;
import com.vscing.model.dto.AdminVipGoodsDto;
import com.vscing.model.dto.AdminVipGroupDto;
import com.vscing.model.mapper.VipGoodsAttachMapper;
import com.vscing.model.mapper.VipGoodsMapper;
import com.vscing.model.mapper.VipGroupMapper;
import com.vscing.model.vo.AdminVipGoodsVo;
import com.vscing.model.vo.AdminVipGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * VipGoodsServiceImpl
 *
 * @author vscing
 * @date 2025/6/2 15:46
 */
@Service
public class VipGoodsServiceImpl implements VipGoodsService {

  @Autowired
  private VipGroupMapper vipGroupMapper;

  @Autowired
  private VipGoodsMapper vipGoodsMapper;

  @Autowired
  private VipGoodsAttachMapper vipGoodsAttachMapper;


  @Override
  public List<AdminVipGroupVo> getGroupList(AdminVipGroupDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return vipGroupMapper.getAdminList(record);
  }

  @Override
  public List<AdminVipGoodsVo> getGoodsList(AdminVipGoodsDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return vipGoodsMapper.getAdminList(record);
  }
}
