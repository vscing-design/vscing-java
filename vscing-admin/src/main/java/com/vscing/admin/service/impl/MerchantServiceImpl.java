package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.MerchantService;
import com.vscing.model.dto.MerchantListDto;
import com.vscing.model.entity.Merchant;
import com.vscing.model.mapper.MerchantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MerchantServiceImpl
 *
 * @author vscing
 * @date 2025/4/19 00:27
 */
@Service
public class MerchantServiceImpl implements MerchantService {

  @Autowired
  private MerchantMapper merchantMapper;

  @Override
  public List<Merchant> getList(MerchantListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return merchantMapper.getList(record);
  }

  @Override
  public int created(Merchant merchant) {
    merchant.setId(IdUtil.getSnowflakeNextId());
    return merchantMapper.insert(merchant);
  }

  @Override
  public int updated(Merchant merchant) {
    return merchantMapper.update(merchant);
  }
}
