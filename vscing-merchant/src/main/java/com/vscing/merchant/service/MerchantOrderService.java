package com.vscing.merchant.service;

import com.vscing.model.dto.MerchantOrderCountDto;
import com.vscing.model.dto.MerchantOrderListDto;
import com.vscing.model.dto.MerchantVipOrderCountDto;
import com.vscing.model.dto.MerchantVipOrderListDto;
import com.vscing.model.vo.MerchantOrderCountVo;
import com.vscing.model.vo.MerchantOrderListVo;
import com.vscing.model.vo.MerchantVipOrderCountVo;
import com.vscing.model.vo.MerchantVipOrderListVo;

import java.util.List;

/**
 * MerchantOrderService
 *
 * @author vscing
 * @date 2025/4/26 09:42
 */
public interface MerchantOrderService {

  /**
   * 电影票订单列表
  */
  List<MerchantOrderListVo> getOrderList(MerchantOrderListDto record, Integer pageSize, Integer pageNum);

  /**
   * 电影票订单统计
   */
  List<MerchantOrderCountVo> getCountList(MerchantOrderCountDto record, Integer pageSize, Integer pageNum);

  /**
   * 会员卡商品订单列表
   */
  List<MerchantVipOrderListVo> getVipOrderList(MerchantVipOrderListDto record, Integer pageSize, Integer pageNum);

  /**
   * 会员卡商品订单统计
   */
  List<MerchantVipOrderCountVo> getVipCountList(MerchantVipOrderCountDto record, Integer pageSize, Integer pageNum);

}
