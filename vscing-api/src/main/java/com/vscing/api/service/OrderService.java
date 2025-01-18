package com.vscing.api.service;

import com.vscing.model.dto.SeatListDto;
import com.vscing.model.request.ShowSeatRequest;
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



}
