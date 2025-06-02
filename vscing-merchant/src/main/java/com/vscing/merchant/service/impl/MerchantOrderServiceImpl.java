package com.vscing.merchant.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.merchant.service.MerchantOrderService;
import com.vscing.model.dto.MerchantOrderCountDto;
import com.vscing.model.dto.MerchantOrderListDto;
import com.vscing.model.dto.MerchantVipOrderCountDto;
import com.vscing.model.dto.MerchantVipOrderListDto;
import com.vscing.model.mapper.MerchantMapper;
import com.vscing.model.vo.MerchantOrderCountVo;
import com.vscing.model.vo.MerchantOrderListVo;
import com.vscing.model.vo.MerchantVipOrderCountVo;
import com.vscing.model.vo.MerchantVipOrderListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * MerchantOrderServiceImpl
 *
 * @author vscing
 * @date 2025/4/26 09:42
 */
@Service
public class MerchantOrderServiceImpl implements MerchantOrderService {

  @Autowired
  private MerchantMapper merchantMapper;

  @Override
  public List<MerchantOrderListVo> getOrderList(MerchantOrderListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    List<MerchantOrderListVo> list = merchantMapper.selectByOrderList(record);
    for (MerchantOrderListVo item : list) {
      BigDecimal productPrice = item.getTotalPrice().divide(BigDecimal.valueOf(item.getPurchaseQuantity()), 2, RoundingMode.HALF_UP);
      item.setProductPrice(productPrice);
    }
    return list;
  }

  @Override
  public List<MerchantOrderCountVo> getCountList(MerchantOrderCountDto record, Integer pageSize, Integer pageNum) {
    return merchantMapper.selectByOrderCount(record);
  }

  @Override
  public List<MerchantVipOrderListVo> getVipOrderList(MerchantVipOrderListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    List<MerchantVipOrderListVo> list = merchantMapper.selectByVipOrderList(record);
    for (MerchantVipOrderListVo item : list) {
      BigDecimal unitPrice = item.getTotalPrice().divide(BigDecimal.valueOf(item.getBuyNum()), 2, RoundingMode.HALF_UP);
      item.setUnitPrice(unitPrice);
    }
    return list;
  }

  @Override
  public List<MerchantVipOrderCountVo> getVipCountList(MerchantVipOrderCountDto record, Integer pageSize, Integer pageNum) {
    return merchantMapper.selectByVipOrderCount(record);
  }

}
