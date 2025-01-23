package com.vscing.api.service;

import com.vscing.model.dto.OrderApiConfirmDetailsDto;
import com.vscing.model.dto.OrderApiCreatedDto;
import com.vscing.model.dto.OrderApiListDto;
import com.vscing.model.dto.OrderApiScoreDto;
import com.vscing.model.dto.SeatListDto;
import com.vscing.model.http.HttpOrder;
import com.vscing.model.request.ShowSeatRequest;
import com.vscing.model.vo.OrderApiConfirmDetailsVo;
import com.vscing.model.vo.OrderApiDetailsVo;
import com.vscing.model.vo.OrderApiListVo;
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
  boolean verifyOrderSeat(OrderApiConfirmDetailsDto orderApiDetails);

  /**
   * 下单确认页详情
   */
  OrderApiConfirmDetailsVo getConfirmDetails(OrderApiConfirmDetailsDto orderApiDetails);

  /**
   * 下单返回小程序参数
  */
  OrderApiPaymentVo create(Long userId, OrderApiCreatedDto orderApiCreatedDto);

  /**
   * 我的订单列表
  */
  List<OrderApiListVo> getList(Long userId, OrderApiListDto queryParam, Integer pageSize, Integer pageNum);

  /**
   * 我的订单详情
   */
  OrderApiDetailsVo getDetails(Long userId, Long id);

  /**
   * 删除订单
   */
  boolean deleteOrder(Long userId, Long id);

  /**
   * 取消订单
   */
  boolean cancelOrder(Long userId, Long id);

  /**
   * 去支付
   */
  OrderApiPaymentVo paymentOrder(Long userId, Long id);

  /**
   * 去出票
   */
  boolean ticketOrder(Long userId, Long id);

  /**
   * 评价
   */
  boolean insertScoreOrder(Long userId, OrderApiScoreDto orderApiScoreDto);

  /**
   * 同步三方订单列表
   */
  boolean supplierOrder(HttpOrder httpOrder);


}
