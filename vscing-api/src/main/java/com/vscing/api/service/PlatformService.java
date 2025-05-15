package com.vscing.api.service;

import com.vscing.model.platform.*;

import java.util.List;

public interface PlatformService {

  /**
   * 验证签名
   */
  boolean verify(String builderStr, QueryDto record);

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
  QueryCinemaShow show(QueryShowDto record);

  /**
   * 座位图
   */
  QuerySeat seat(QuerySeatDto record);

  /**
   * 提交订单
   */
  QueryOrder submitOrder(QuerySubmitOrderDto record);

}
