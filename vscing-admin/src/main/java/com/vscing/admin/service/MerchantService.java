package com.vscing.admin.service;

import com.vscing.model.dto.MerchantListDto;
import com.vscing.model.entity.Merchant;

import java.util.List;

/**
 * MerchantService
 *
 * @author vscing
 * @date 2025/4/19 00:27
 */
public interface MerchantService {

  /**
   * 列表
   */
  List<Merchant> getList(MerchantListDto record, Integer pageSize, Integer pageNum);

  /**
   * 新增
   */
  int created(Merchant merchant);

  /**
   * 编辑
   */
  int updated(Merchant merchant);

}
