package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.MerchantBankService;
import com.vscing.model.dto.MerchantBankListDto;
import com.vscing.model.entity.MerchantBank;
import com.vscing.model.mapper.MerchantBankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MerchantBankServiceImpl
 *
 * @author vscing
 * @date 2025/4/19 16:27
 */
@Service
public class MerchantBankServiceImpl implements MerchantBankService {

  @Autowired
  private MerchantBankMapper merchantBankMapper;

  @Override
  public List<MerchantBank> getList(MerchantBankListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return merchantBankMapper.getList(record);
  }

  @Override
  public int created(MerchantBank record) {
    record.setId(IdUtil.getSnowflakeNextId());
    return merchantBankMapper.insert(record);
  }

  @Override
  public int updated(MerchantBank record) {
    return merchantBankMapper.update(record);
  }

  @Override
  public long deleted(long id, long deleterId) {
    return merchantBankMapper.softDeleteById(id, deleterId);
  }
}
