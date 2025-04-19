package com.vscing.admin.service;

import com.vscing.model.dto.MerchantBankListDto;
import com.vscing.model.entity.MerchantBank;

import java.util.List;

/**
 * MerchantBankService
 *
 * @author vscing
 * @date 2025/4/19 16:25
 */
public interface MerchantBankService {

  /**
   * 列表
   */
  List<MerchantBank> getList(MerchantBankListDto record, Integer pageSize, Integer pageNum);

  /**
   * 新增
   */
  int created(MerchantBank record);

  /**
   * 编辑
   */
  int updated(MerchantBank record);

  /**
   * 删除
   */
  long deleted(long id, long deleterId);

}
