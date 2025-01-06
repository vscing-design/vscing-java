package com.vscing.model.mapper;

import com.vscing.model.dto.OrderListDto;
import com.vscing.model.entity.Order;
import com.vscing.model.vo.OrderDetailVo;
import com.vscing.model.vo.OrderPriceVo;
import com.vscing.model.vo.OrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:55:01
*/
@Mapper
public interface OrderMapper {

  List<OrderVo> getList(OrderListDto record);

  OrderPriceVo getOrderPrice(OrderListDto data);

  Order selectById(long id);

  OrderDetailVo selectEditById(long id);

  int insert(Order record);

  int update(Order record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int cancelPendingPayments();

  List<Order> getPendingTicketOrders();
  
}
