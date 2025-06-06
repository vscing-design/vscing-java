package com.vscing.model.mapper;

import com.vscing.model.dto.AdminVipOrderDto;
import com.vscing.model.entity.VipOrder;
import com.vscing.model.platform.QueryOrderTicketDto;
import com.vscing.model.platform.QueryVipOrderTicket;
import com.vscing.model.vo.AdminVipOrderVo;
import com.vscing.model.vo.OrderPriceVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * VipOrderMapper
 *
 * @author vscing
 * @date 2025/6/2 17:41
 */
@Mapper
public interface VipOrderMapper {

  /**
   * 开放平台查订单详情
   */
  QueryVipOrderTicket getPlatformInfo(QueryOrderTicketDto record);

  /**
   * 管理端查询列表
   */
  List<AdminVipOrderVo> getAdminList(AdminVipOrderDto record);

  /**
   * 管理端金额统计
   */
  OrderPriceVo getCountAmount(AdminVipOrderDto record);

  /**
   * 插入数据源
   */
  int insert(VipOrder record);

  /**
   * 修改数据源
   */
  int update(VipOrder record);

  /**
   * 根据订单号查订单详情
   */
  VipOrder selectByOrderSn(String orderSn);

  /**
   * 根据订单id查订单详情
   */
  VipOrder selectById(Long id);

}
