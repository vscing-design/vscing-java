package com.vscing.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.ShowService;
import com.vscing.common.api.ResultCode;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.utils.HttpClientUtil;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.model.dto.PricingRuleListDto;
import com.vscing.model.dto.ShowAllDto;
import com.vscing.model.dto.ShowListDto;
import com.vscing.model.entity.PricingRule;
import com.vscing.model.entity.Show;
import com.vscing.model.entity.ShowArea;
import com.vscing.model.mapper.PricingRuleMapper;
import com.vscing.model.mapper.ShowAreaMapper;
import com.vscing.model.mapper.ShowMapper;
import com.vscing.model.request.SeatPriceRequest;
import com.vscing.model.request.ShowSeatRequest;
import com.vscing.model.utils.PricingUtil;
import com.vscing.model.vo.MovieTreeVo;
import com.vscing.model.vo.SeatMapVo;
import com.vscing.model.vo.SeatPriceMapVo;
import com.vscing.model.vo.SeatVo;
import com.vscing.model.vo.ShowListVo;
import com.vscing.model.vo.ShowTreeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ShowServiceImpl
 *
 * @author vscing
 * @date 2024/12/28 19:22
 */
@Slf4j
@Service
public class ShowServiceImpl implements ShowService {

  @Autowired
  private ShowMapper showMapper;

  @Autowired
  private ShowAreaMapper showAreaMapper;

  @Autowired
  private PricingRuleMapper pricingRuleMapper;

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public boolean initShow(Show show, List<ShowArea> showAreaList) {
    try {

      // 创建
      int rowsAffected = showMapper.insert(show);
      if (rowsAffected <= 0) {
        throw new ServiceException("新增失败");
      }
      // 增加机构
      rowsAffected = showAreaMapper.batchInsert(showAreaList);
      if (rowsAffected != showAreaList.size()) {
        throw new ServiceException("新增关联失败");
      }
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
    return true;
  }

  @Override
  public List<ShowListVo> getList(ShowListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    // 查询主表数据
    List<ShowListVo> list = showMapper.getList(data);

    if (list == null || list.isEmpty()) {
      return CollUtil.newArrayList();
    }

    // 提取所有 showId 并进行批量查询
    List<Long> showIds = list.stream()
        .map(ShowListVo::getId)
        .distinct() // 确保唯一性，避免重复查询
        .collect(Collectors.toList());

    // 批量查询从表数据
    List<ShowArea> showAreaList = Optional.ofNullable(showAreaMapper.selectByShowIds(showIds))
        .orElse(CollUtil.newArrayList());

    // 构建 showId 到 ShowArea 列表的映射
    Map<Long, List<ShowArea>> showAreaMap = showAreaList.stream()
        .collect(Collectors.groupingBy(ShowArea::getShowId));

    // 设置关联的 ShowArea 列表到 ShowListVo 对象中
    for (ShowListVo show : list) {
      show.setShowAreaList(showAreaMap.getOrDefault(show.getId(), CollUtil.newArrayList()));
    }

    return list;
  }

  @Override
  public List<MovieTreeVo> getAll(ShowAllDto data) {
    List<ShowListVo> list = showMapper.getListByCinemaId(data);
    // 设置影片列表
    List<MovieTreeVo> movieList = new ArrayList<>();
    // 构建 showId 到 ShowTreeVo 的映射
    Map<Long, List<ShowTreeVo>> showMap = new HashMap<>();
    // 遍历列表
    for (ShowListVo show : list) {
      // 构建 MovieTreeVo
      MovieTreeVo movieTree = MapstructUtils.convert(show, MovieTreeVo.class);
      if (!movieList.contains(movieTree)) {
        movieList.add(movieTree);
      }
      // 构建 ShowTreeVo
      ShowTreeVo showTree = MapstructUtils.convert(show, ShowTreeVo.class);
      showTree.setShowId(show.getId());
      // 判断
      if(showMap.containsKey(show.getMovieId())) {
        showMap.get(show.getMovieId()).add(showTree);
      } else {
        showMap.put(show.getMovieId(), CollUtil.newArrayList(showTree));
      }
    }
    // 组装数据
    for (MovieTreeVo movie : movieList) {
      movie.setShowList(showMap.getOrDefault(movie.getMovieId(), CollUtil.newArrayList()));
    }

    return movieList;
  }

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

      // 发送请求并获取响应
      String responseBody = HttpClientUtil.postRequest(
          "https://test.ot.jfshou.cn/ticket/ticket_api/seat/query",
          params
      );
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
  public SeatPriceMapVo getSeatPrice(SeatPriceRequest seatPrice) {
    SeatPriceMapVo seatPriceMapVo = new SeatPriceMapVo();
    // 获取场次ID
    Long showId = seatPrice.getShowId();
    // 获取场次全局价格
    Show show = showMapper.selectById(showId);
    // 获取结算规则列表
    List<PricingRule> pricingRules = pricingRuleMapper.getList(new PricingRuleListDto());
    if(show == null || pricingRules == null) {
      return seatPriceMapVo;
    }
    // 获取区域ID
    List<String> areas = seatPrice.getAreaIds().stream()
        // 过滤掉 null 和空字符串
        .filter(item -> item != null && !item.trim().isEmpty())
        // 去重
        .distinct()
        .collect(Collectors.toList());
    // 构建区域价格映射
    Map<String, ShowArea> areaPriceMap = new HashMap<>(0);
    if(areas != null && !areas.isEmpty()) {
      // 获取区域价格
      List<ShowArea> showAreaList = showAreaMapper.selectByShowIdAreas(showId, areas);
      // 构建区域价格映射
      areaPriceMap = showAreaList.stream()
          .collect(Collectors.toMap(
              ShowArea::getArea,
              area -> area,
              // 处理重复键的情况（如果有的话）
              (existing, replacement) -> existing
          ));
    }

    // 遍历区域ID，填充最终结果
    List<BigDecimal> salesPrice = new ArrayList<>();
    List<BigDecimal> supplyPrice = new ArrayList<>();
    List<BigDecimal> profit = new ArrayList<>();
    for (String area : seatPrice.getAreaIds()) {
      ShowArea areaPrice = areaPriceMap.get(area);
      // 场次价格（元）
      BigDecimal showPrice = show.getShowPrice();
      // 影片结算价（元）
      BigDecimal userPrice = show.getUserPrice();
      if (areaPrice != null) {
        // 场次价格（元）
        showPrice = areaPrice.getShowPrice();
        // 影片结算价（元）
        userPrice = areaPrice.getUserPrice();
      }
      // 实际销售价格
      BigDecimal price = PricingUtil.calculateActualPrice(showPrice, userPrice, pricingRules);
      // 统计
      salesPrice.add(price);
      supplyPrice.add(userPrice);
      BigDecimal diffPrice = price.subtract(userPrice);
      profit.add(diffPrice);
    }
    // 赋值
    seatPriceMapVo.setSalesPrice(PricingUtil.formatPrices(salesPrice));
    seatPriceMapVo.setSupplyPrice(PricingUtil.formatPrices(supplyPrice));
    seatPriceMapVo.setProfit(PricingUtil.formatPrices(profit));

    return seatPriceMapVo;
  }

}
