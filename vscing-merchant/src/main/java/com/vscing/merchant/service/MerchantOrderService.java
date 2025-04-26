package com.vscing.merchant.service;

import com.vscing.model.dto.MerchantOrderCountDto;
import com.vscing.model.dto.MerchantOrderListDto;
import com.vscing.model.vo.MerchantOrderCountVo;
import com.vscing.model.vo.MerchantOrderListVo;

import java.util.List;

/**
 * MerchantOrderService
 *
 * @author vscing
 * @date 2025/4/26 09:42
 */
public interface MerchantOrderService {

  /**
   * 订单列表
  */
  List<MerchantOrderListVo> getOrderList(MerchantOrderListDto record, Integer pageSize, Integer pageNum);

  /**
   * 订单统计
   */
  List<MerchantOrderCountVo> getCountList(MerchantOrderCountDto record, Integer pageSize, Integer pageNum);

}
