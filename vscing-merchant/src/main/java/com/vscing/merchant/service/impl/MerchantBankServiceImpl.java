package com.vscing.merchant.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.merchant.service.MerchantBankService;
import com.vscing.model.entity.MerchantBank;
import com.vscing.model.mapper.MerchantBankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MerchantBankServiceImpl
 *
 * @author vscing
 * @date 2025/4/26 00:20
 */
@Service
public class MerchantBankServiceImpl implements MerchantBankService {

  @Autowired
  private MerchantBankMapper merchantBankMapper;

  @Override
  public List<MerchantBank> getBankList(Long merchantId, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return merchantBankMapper.getBankList(merchantId);
  }
}
