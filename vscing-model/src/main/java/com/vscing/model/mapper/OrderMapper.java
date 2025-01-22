package com.vscing.model.mapper;

import com.vscing.model.dto.OrderListDto;
import com.vscing.model.entity.Order;
import com.vscing.model.vo.OrderApiDetailsVo;
import com.vscing.model.vo.OrderApiListVo;
import com.vscing.model.vo.OrderDetailVo;
import com.vscing.model.vo.OrderPriceVo;
import com.vscing.model.vo.OrderVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:55:01
*/
@Mapper
public interface OrderMapper {

  List<OrderApiListVo> getApiList(@Param("userId") long userId, @Param("status") String status, @Param("platform") Integer platform);

  OrderApiDetailsVo getApiDetails(@Param("userId") long userId, @Param("id") long id);

  List<OrderVo> getList(OrderListDto record);

  OrderPriceVo getOrderPrice(OrderListDto data);

  Order selectById(long id);

  OrderDetailVo selectEditById(long id);

  Order selectByOrderSn(String orderSn);

  @Insert("INSERT INTO vsc_order_score (user_id, order_id, score) values (#{userId, jdbcType=BIGINT}, #{orderId, jdbcType=BIGINT}, #{score, jdbcType=TINYINT})")
  int insertScore(@Param("userId") long userId, @Param("orderId") long orderId, @Param("score") int score);

  int insert(Order record);

  int update(Order record);

  int updateAlipayOrder(@Param("orderSn") String orderSn, @Param("tradeNo") String tradeNo);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int cancelPendingPayments();

  List<Order> getPendingTicketOrders();

  boolean checkOrderShowSeat(@Param("showId") Long showId, @Param("seatIds") List<String> seatIds);
  
}
