package com.vscing.api.service;

import com.vscing.model.entity.Merchant;
import com.vscing.model.platform.*;

import java.util.List;

public interface PlatformService {

  /**
   * 获取商户信息
   */
  Merchant getMerchant(QueryDto record);

  /**
   * 验证签名
   */
  boolean verify(String builderStr, QueryDto record, Merchant merchant);

  /**
   * 城市列表
   */
  List<QueryCity> city(QueryCityDto record);

  /**
   * 影院列表
   */
  List<QueryCinema> cinema(QueryCinemaDto record);

  /**
   * 影片列表
   */
  List<QueryMovie> movie(QueryMovieDto record);

  /**
   * 场次列表
   */
  QueryCinemaShow show(QueryShowDto record, Merchant merchant);

  /**
   * 座位图
   */
  QuerySeat seat(QuerySeatDto record);

  /**
   * 提交订单
   */
  QueryOrder submitOrder(QuerySubmitOrderDto record, Merchant merchant);

  /**
   * 查询订单
   */
  QueryOrderTicket orderTicket(QueryOrderTicketDto record);

}
