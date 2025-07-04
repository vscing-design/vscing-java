package com.vscing.admin.service;

import com.vscing.model.dto.OrderListDto;
import com.vscing.model.http.HttpOrder;
import com.vscing.model.request.OrderChangeRequest;
import com.vscing.model.request.OrderSaveRequest;
import com.vscing.model.vo.OrderDetailVo;
import com.vscing.model.vo.OrderPriceVo;
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

  /**
   * 详情
   */
  OrderDetailVo getDetails(Long id);

  /**
   * 统计订单价格
   */
  OrderPriceVo getOrderPrice(OrderListDto data);

  /**
   * 校验座位是否存在订单中
   */
  boolean verifyOrderSeat(OrderSaveRequest orderSave);

  /**
   * 手动下单
   */
  boolean createOrder(OrderSaveRequest orderSave, Long by);

  /**
   * 调座
   */
  boolean changeOrder(OrderChangeRequest orderChange, Long by);

  /**
   * 取消订单
   */
  boolean cancelOrder(Long id, Long by);

  /**
   * 退款
   */
  boolean refundOrder(Long id, Long by);

  /**
   * 取票
   */
  boolean ticketOrder(Long id, Long by);

  /**
   * 同步三方订单列表 作废了
   */
  boolean supplierOrder(HttpOrder httpOrder);

  /**
   * 订单关闭-仅支持商户订单
   */
  void closeOrder(Long id, Long by);

}
