package com.vscing.merchant.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.merchant.service.MerchantGoodsService;
import com.vscing.model.dto.MerchantGoodsListDto;
import com.vscing.model.dto.MerchantPriceVipGoodsDto;
import com.vscing.model.entity.MerchantPrice;
import com.vscing.model.mapper.MerchantPriceMapper;
import com.vscing.model.mapper.VipGoodsMapper;
import com.vscing.model.vo.MerchantGoodsListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MerchantGoodsServiceImpl
 *
 * @author vscing
 * @date 2025/6/2 21:52
 */
@Service
public class MerchantGoodsServiceImpl implements MerchantGoodsService {

  @Autowired
  private VipGoodsMapper vipGoodsMapper;

  @Autowired
  private MerchantPriceMapper merchantPriceMapper;

  @Override
  public List<MerchantGoodsListVo> getVipGoodsList(MerchantGoodsListDto record, Integer pageSize, Integer pageNum) {
    // 分页查询列表
    PageHelper.startPage(pageNum, pageSize);
    List<MerchantGoodsListVo> list = vipGoodsMapper.getMerchantList(record);
    // 获取商品id集合
    List<Long> vipGoodsIds = list.stream()
        .map(MerchantGoodsListVo::getId)
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
    for (MerchantGoodsListVo merchantGoodsListVo : list) {
      // 加价
      BigDecimal markupPrice = new BigDecimal("1.00");
      if(priceMap.get(merchantGoodsListVo.getId()) != null) {
        markupPrice = priceMap.get(merchantGoodsListVo.getId());
      }
      // 空值检查
      if (merchantGoodsListVo.getGoodsPrice() != null) {
        BigDecimal newGoodsPrice = merchantGoodsListVo.getGoodsPrice().add(markupPrice);
        merchantGoodsListVo.setGoodsPrice(newGoodsPrice);

        // 检查市场价是否为 null 或零
        if (merchantGoodsListVo.getMarketPrice() != null && merchantGoodsListVo.getMarketPrice().compareTo(BigDecimal.ZERO) != 0) {
          BigDecimal discount = newGoodsPrice.divide(merchantGoodsListVo.getMarketPrice(), RoundingMode.HALF_UP)
              .multiply(BigDecimal.valueOf(100)); // 这里推测原代码想计算百分比，应该是乘以 100 而不是除以 100
          merchantGoodsListVo.setDiscount(discount);
        } else {
          // 市场价为 null 或零，设置折扣为 0
          merchantGoodsListVo.setDiscount(BigDecimal.ZERO);
        }
      } else {
        // 商品价格为 null，设置折扣为 0
        merchantGoodsListVo.setDiscount(BigDecimal.ZERO);
      }
    }
    return list;
  }
}
