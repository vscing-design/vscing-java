package com.vscing.admin.service;

import com.vscing.model.dto.AdminVipOrderDto;
import com.vscing.model.vo.AdminVipOrderVo;

import java.util.List;

/**
 * VipOrderService
 *
 * @author vscing
 * @date 2025/6/2 17:39
 */
public interface VipOrderService {

  /**
   * 会员商品分组列表
   */
  List<AdminVipOrderVo> getList(AdminVipOrderDto record, Integer pageSize, Integer pageNum);

}
