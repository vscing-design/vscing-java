package com.vscing.api.service;

import com.vscing.model.dto.OrderApiCreatedDto;
import com.vscing.model.dto.OrderApiDetailsDto;
import com.vscing.model.dto.SeatListDto;
import com.vscing.model.request.ShowSeatRequest;
import com.vscing.model.vo.OrderApiDetailsVo;
import com.vscing.model.vo.OrderApiPaymentVo;
import com.vscing.model.vo.SeatMapVo;

import java.util.List;

/**
 * OrderService
 *
 * @author vscing
 * @date 2025/1/18 20:04
 */
public interface OrderService {

  /**
   * 选中座位列表
   */
  List<SeatListDto> seatList(ShowSeatRequest showSeat);

  /**
   * 三方座位列表
  */
  SeatMapVo getSeat(ShowSeatRequest showSeat);

  /**
   * 校验座位是否存在订单中
   */
  boolean verifyOrderSeat(OrderApiDetailsDto orderApiDetails);

  /**
   * 下单确认页详情
   */
  OrderApiDetailsVo getDetails(OrderApiDetailsDto orderApiDetails);

  /**
   * 下单返回小程序参数
  */
  OrderApiPaymentVo create(Long userId, OrderApiCreatedDto orderApiCreatedDto);

}
