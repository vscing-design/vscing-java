package com.vscing.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.OrderService;
import com.vscing.model.dto.OrderListDto;
import com.vscing.model.mapper.OrderMapper;
import com.vscing.model.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OrderServiceImpl
 *
 * @author vscing
 * @date 2024/12/31 01:30
 */
@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderMapper orderMapper;

  @Override
  public List<OrderVo> getList(OrderListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return orderMapper.getList(data);
  }

}
