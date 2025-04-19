package com.vscing.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.MerchantPriceService;
import com.vscing.model.dto.MerchantPriceListDto;
import com.vscing.model.entity.MerchantPrice;
import com.vscing.model.mapper.MerchantPriceMapper;
import com.vscing.model.vo.MerchantPriceListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MerchantPriceServiceImpl
 *
 * @author vscing
 * @date 2025/4/19 16:16
 */
@Service
public class MerchantPriceServiceImpl implements MerchantPriceService {

  @Autowired
  private MerchantPriceMapper merchantPriceMapper;

  @Override
  public List<MerchantPriceListVo> getList(MerchantPriceListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return merchantPriceMapper.getList(record);
  }

  @Override
  public int updated(MerchantPrice record) {
    return merchantPriceMapper.update(record);
  }
}
