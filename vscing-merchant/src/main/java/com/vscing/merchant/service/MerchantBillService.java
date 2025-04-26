package com.vscing.merchant.service;

import com.vscing.model.dto.MerchantBillListDto;
import com.vscing.model.dto.MerchantBillRechargeDto;
import com.vscing.model.entity.MerchantBill;
import com.vscing.model.vo.MerchantDetailVo;

import java.util.List;

/**
 * MerchantBillService
 *
 * @author vscing
 * @date 2025/4/26 00:35
 */
public interface MerchantBillService {

  /**
   * 充值
   */
  List<MerchantBill> getList(MerchantBillListDto record, Integer pageSize, Integer pageNum);

  /**
   * 充值
  */
  void recharge(MerchantBillRechargeDto record, MerchantDetailVo merchant);

}
