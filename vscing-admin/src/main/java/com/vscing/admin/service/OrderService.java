package com.vscing.admin.service;

import com.vscing.model.dto.OrderListDto;
import com.vscing.model.vo.OrderVo;

import java.util.List;

/**
 * OrderService
 *
 * @author vscing
 * @date 2024/12/31 01:29
 */
public interface OrderService {

  /**
   * 列表
   */
  List<OrderVo> getList(OrderListDto data, Integer pageSize, Integer pageNum);

}
