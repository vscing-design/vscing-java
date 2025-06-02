package com.vscing.merchant.service;

import com.vscing.model.dto.MerchantGoodsListDto;
import com.vscing.model.vo.MerchantGoodsListVo;

import java.util.List;

/**
 * MerchantGoodsService
 *
 * @author vscing
 * @date 2025/6/2 21:52
 */
public interface MerchantGoodsService {

  /**
   * 查询会员卡商品列表
  */
  List<MerchantGoodsListVo> getVipGoodsList(MerchantGoodsListDto record, Integer pageSize, Integer pageNum);

}
