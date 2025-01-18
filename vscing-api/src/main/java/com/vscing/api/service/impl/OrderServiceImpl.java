package com.vscing.api.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.api.service.OrderService;
import com.vscing.common.api.ResultCode;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.supplier.SupplierService;
import com.vscing.common.service.supplier.SupplierServiceFactory;
import com.vscing.model.dto.SeatListDto;
import com.vscing.model.entity.Show;
import com.vscing.model.mapper.OrderDetailMapper;
import com.vscing.model.mapper.ShowMapper;
import com.vscing.model.request.ShowSeatRequest;
import com.vscing.model.vo.SeatMapVo;
import com.vscing.model.vo.SeatVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OrderServiceImpl
 *
 * @author vscing
 * @date 2025/1/18 20:04
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private SupplierServiceFactory supplierServiceFactory;

  @Autowired
  private ShowMapper showMapper;

  @Autowired
  private OrderDetailMapper orderDetailMapper;

  @Override
  public SeatMapVo getSeat(ShowSeatRequest showSeat) {
    // 初始化数据
    SeatMapVo result = new SeatMapVo();
    List<SeatVo> seatList = new ArrayList<>();
    result.setRestrictions(0);
    result.setSeats(seatList);
    // 获取场次Id参数
    Long showId = showSeat.getShowId();
    Show show = showMapper.selectById(showId);
    if(show == null) {
      return result;
    }
    try {
      // 准备请求参数
      Map<String, String> params = new HashMap<>();
      params.put("showId", show.getTpShowId());
      params.put("addFlag", String.valueOf(showSeat.getAddFlag()));
      SupplierService supplierService = supplierServiceFactory.getSupplierService("jfshou");
      // 发送请求并获取响应
      String responseBody = supplierService.sendRequest("/seat/query", params);
      log.info("responseBody: {}", responseBody);

      // 将 JSON 字符串解析为 JsonNode 对象
      ObjectMapper objectMapper = new ObjectMapper();

      Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
      Integer code = (Integer) responseMap.get("code");
      if(code != ResultCode.SUCCESS.getCode()) {
        return result;
      }
      Map<String, Object> data = (Map<String, Object>) responseMap.get("data");
      Integer restrictions = (Integer) data.get("restrictions");
      List<Map<String, Object>> seatListRaw = (List<Map<String, Object>>) data.get("seats");
      // 遍历座位列表并解析 seatNo，设置 pai 和 lie
      if (seatListRaw != null && !seatListRaw.isEmpty()) {
        for (Map<String, Object> seatRaw : seatListRaw) {
          SeatVo seat = new SeatVo();
          String columnNo = (String) seatRaw.get("columnNo");
          String rowNo = (String) seatRaw.get("rowNo");
          String seatNo = (String) seatRaw.get("seatNo");
          String status = (String) seatRaw.get("status");
          Integer loveStatus = (Integer) seatRaw.get("loveStatus");
          String seatId = (String) seatRaw.get("seatId");
          String areaId = (String) seatRaw.get("areaId");
          seat.setColumnNo(columnNo);
          seat.setRowNo(rowNo);
          seat.setSeatNo(seatNo);
          seat.setStatus(status);
          seat.setLoveStatus(loveStatus);
          seat.setSeatId(seatId);
          seat.setAreaId(areaId);
          seat.parseAndSetPaiLie();
          seatList.add(seat);
        }
      }
      // 设置返回结果
      result.setRestrictions(restrictions);
      result.setSeats(seatList);

      return result;
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
  }

  @Override
  public List<SeatListDto> seatList(ShowSeatRequest showSeat) {
    return orderDetailMapper.selectByShowId(showSeat.getShowId());
  }

}
