package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.VipGoodsService;
import com.vscing.common.exception.ServiceException;
import com.vscing.model.dto.AdminVipGoodsDto;
import com.vscing.model.dto.AdminVipGoodsPricingDto;
import com.vscing.model.dto.AdminVipGroupDto;
import com.vscing.model.dto.MerchantPriceVipGoodsDto;
import com.vscing.model.entity.MerchantPrice;
import com.vscing.model.mapper.MerchantPriceMapper;
import com.vscing.model.mapper.VipGoodsMapper;
import com.vscing.model.mapper.VipGroupMapper;
import com.vscing.model.request.AdminVipGoodsPricingRequest;
import com.vscing.model.vo.AdminVipGoodsPricingVo;
import com.vscing.model.vo.AdminVipGoodsVo;
import com.vscing.model.vo.AdminVipGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  private MerchantPriceMapper merchantPriceMapper;

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

  @Override
  public List<AdminVipGoodsPricingVo> getGoodsPricingList(AdminVipGoodsPricingDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    List<AdminVipGoodsPricingVo> list = vipGoodsMapper.getAdminPricingList(record);
    // 获取商品id集合
    List<Long> vipGoodsIds = list.stream()
        .map(AdminVipGoodsPricingVo::getGoodsId)
        .toList();
    // 根据商品id集合、商户id
    MerchantPriceVipGoodsDto merchantPriceVipGoodsDto = new MerchantPriceVipGoodsDto();
    merchantPriceVipGoodsDto.setMerchantId(record.getMerchantId());
    merchantPriceVipGoodsDto.setVipGoodsIds(vipGoodsIds);
    // 获取价格列表
    List<MerchantPrice> priceList = merchantPriceMapper.getVipGoodsMarkupList(merchantPriceVipGoodsDto);
    Map<Long, BigDecimal> priceMap = new HashMap<>();
    for (MerchantPrice price : priceList) {
      priceMap.put(price.getVipGoodsId(), price.getMarkupAmount());
    }
    // 遍历数据列表
    for (AdminVipGoodsPricingVo adminVipGoodsPricingVo : list) {
      // 空值检查
      if (priceMap.get(adminVipGoodsPricingVo.getGoodsId()) != null) {
        adminVipGoodsPricingVo.setMarkupPrice(priceMap.get(adminVipGoodsPricingVo.getGoodsId()));
      } else {
        adminVipGoodsPricingVo.setMarkupPrice(new BigDecimal("1.00"));
      }
    }
    return list;
  }

  @Override
  public void vipGoodsPricing(List<AdminVipGoodsPricingRequest> record, Long by) {
    try {
      for (AdminVipGoodsPricingRequest vo : record) {
        vo.setId(IdUtil.getSnowflakeNextId());
        vo.setCreatedBy(by);
      }
      int rowsAffected = merchantPriceMapper.batchInsert(record);
      if (rowsAffected < 0) {
        throw new ServiceException("无变更项");
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

}
