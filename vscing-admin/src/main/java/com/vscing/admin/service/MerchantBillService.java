package com.vscing.admin.service;

import com.vscing.model.dto.MerchantBillListDto;
import com.vscing.model.entity.MerchantBill;

import java.util.List;

/**
 * MerchantBillService
 *
 * @author vscing
 * @date 2025/4/19 16:33
 */
public interface MerchantBillService {

  /**
   * 列表
   */
  List<MerchantBill> getList(MerchantBillListDto record, Integer pageSize, Integer pageNum);

}
