package com.vscing.merchant.service;

import com.vscing.model.entity.MerchantBank;

import java.util.List;

/**
 * MerchantBankService
 * @date 2025/4/26 00:20
 * @author vscing
 */
public interface MerchantBankService {

  /**
   * 查询商户对公户接口列表
  */
  List<MerchantBank> getBankList(Long merchantId, Integer pageSize, Integer pageNum);

}
