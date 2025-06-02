package com.vscing.merchant.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.merchant.service.MerchantGoodsService;
import com.vscing.model.dto.MerchantGoodsListDto;
import com.vscing.model.mapper.VipGoodsMapper;
import com.vscing.model.vo.MerchantGoodsListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

  @Override
  public List<MerchantGoodsListVo> getVipGoodsList(MerchantGoodsListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    List<MerchantGoodsListVo> list = vipGoodsMapper.getMerchantList(record);
    for (MerchantGoodsListVo merchantGoodsListVo : list) {
      // 空值检查
      if (merchantGoodsListVo.getGoodsPrice() != null && merchantGoodsListVo.getMarkupPrice() != null) {
        BigDecimal newGoodsPrice = merchantGoodsListVo.getGoodsPrice().add(merchantGoodsListVo.getMarkupPrice());
        merchantGoodsListVo.setGoodsPrice(newGoodsPrice);

        // 检查市场价是否为 null 或零
        if (merchantGoodsListVo.getMarketPrice() != null && merchantGoodsListVo.getMarketPrice().compareTo(BigDecimal.ZERO) != 0) {
          BigDecimal discount = newGoodsPrice.divide(merchantGoodsListVo.getMarketPrice(), 4, BigDecimal.ROUND_HALF_UP)
              .multiply(BigDecimal.valueOf(100)); // 这里推测原代码想计算百分比，应该是乘以 100 而不是除以 100
          merchantGoodsListVo.setDiscount(discount);
        } else {
          // 市场价为 null 或零，设置折扣为 0
          merchantGoodsListVo.setDiscount(BigDecimal.ZERO);
        }
      } else {
        // 商品价格或加价为 null，设置折扣为 0
        merchantGoodsListVo.setDiscount(BigDecimal.ZERO);
      }
      merchantGoodsListVo.setMarkupPrice(null);
    }
    return list;
  }
}
