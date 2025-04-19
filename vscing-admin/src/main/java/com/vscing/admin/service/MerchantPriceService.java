package com.vscing.admin.service;

import com.vscing.model.dto.MerchantPriceListDto;
import com.vscing.model.entity.MerchantPrice;
import com.vscing.model.vo.MerchantPriceListVo;

import java.util.List;

/**
 * MerchantPriceService
 *
 * @author vscing
 * @date 2025/4/19 16:16
 */
public interface MerchantPriceService {

  /**
   * 列表
   */
  List<MerchantPriceListVo> getList(MerchantPriceListDto record, Integer pageSize, Integer pageNum);

  /**
   * 编辑
   */
  int updated(MerchantPrice record);

}
