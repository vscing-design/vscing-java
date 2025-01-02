package com.vscing.admin.service;

import com.vscing.model.dto.ShowAllDto;
import com.vscing.model.dto.ShowListDto;
import com.vscing.model.entity.Show;
import com.vscing.model.entity.ShowArea;
import com.vscing.model.request.SeatPriceRequest;
import com.vscing.model.request.ShowSeatRequest;
import com.vscing.model.vo.MovieTreeVo;
import com.vscing.model.vo.SeatMapVo;
import com.vscing.model.vo.SeatPriceMapVo;
import com.vscing.model.vo.ShowListVo;

import java.util.List;

/**
 * ShowService
 *
 * @author vscing
 * @date 2024/12/28 19:21
 */
public interface ShowService {

  boolean initShow(Show show, List<ShowArea> showAreaList);

  List<ShowListVo> getList(ShowListDto data, Integer pageSize, Integer pageNum);

  List<MovieTreeVo> getAll(ShowAllDto data);

  SeatMapVo getSeat(ShowSeatRequest showSeat);

  SeatPriceMapVo getSeatPrice(SeatPriceRequest data);

}
