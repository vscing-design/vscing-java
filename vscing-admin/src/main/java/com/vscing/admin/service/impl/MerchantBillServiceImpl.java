package com.vscing.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.MerchantBillService;
import com.vscing.model.dto.MerchantBillListDto;
import com.vscing.model.entity.MerchantBill;
import com.vscing.model.mapper.MerchantBillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MerchantBillServiceImpl
 *
 * @author vscing
 * @date 2025/4/19 16:34
 */
@Service
public class MerchantBillServiceImpl implements MerchantBillService {

  @Autowired
  private MerchantBillMapper merchantBillMapper;

  @Override
  public List<MerchantBill> getList(MerchantBillListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return merchantBillMapper.getList(record);
  }
}
