package com.vscing.api.service;

import com.vscing.model.entity.Merchant;
import com.vscing.model.platform.QueryOrder;
import com.vscing.model.platform.QueryOrderTicketDto;
import com.vscing.model.platform.QuerySubmitVipOrderDto;
import com.vscing.model.platform.QueryVipGoods;
import com.vscing.model.platform.QueryVipGoodsDetails;
import com.vscing.model.platform.QueryVipGoodsDetailsDto;
import com.vscing.model.platform.QueryVipGoodsDto;
import com.vscing.model.platform.QueryVipGroup;
import com.vscing.model.platform.QueryVipGroupDto;
import com.vscing.model.platform.QueryVipOrderTicket;

import java.util.List;

public interface PlatformVipGoodsService {

  /**
   * 查询会员卡商品分组列表
   */
  List<QueryVipGroup> group(QueryVipGroupDto record);

  /**
   * 查询会员卡商品列表
   */
  List<QueryVipGoods> all(QueryVipGoodsDto record);

  /**
   * 查询会员卡商品详情
   */
  QueryVipGoodsDetails details(QueryVipGoodsDetailsDto record);

  /**
   * 会员卡商品下单
   */
  QueryOrder submitOrder(QuerySubmitVipOrderDto record, Merchant merchant);

  /**
   * 会员卡商品订单详情
   */
  QueryVipOrderTicket queryOrder(QueryOrderTicketDto record);

}
