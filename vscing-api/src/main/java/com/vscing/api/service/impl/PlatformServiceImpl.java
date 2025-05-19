package com.vscing.api.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.api.service.NotifyService;
import com.vscing.api.service.PlatformService;
import com.vscing.common.api.ResultCode;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.RedisService;
import com.vscing.common.service.supplier.SupplierService;
import com.vscing.common.service.supplier.SupplierServiceFactory;
import com.vscing.common.utils.JsonUtils;
import com.vscing.common.utils.OrderUtils;
import com.vscing.common.utils.SignatureGenerator;
import com.vscing.model.entity.Merchant;
import com.vscing.model.entity.MerchantBill;
import com.vscing.model.entity.MerchantPrice;
import com.vscing.model.entity.Order;
import com.vscing.model.entity.OrderDetail;
import com.vscing.model.entity.Show;
import com.vscing.model.entity.ShowArea;
import com.vscing.model.mapper.CinemaMapper;
import com.vscing.model.mapper.CityMapper;
import com.vscing.model.mapper.DistrictMapper;
import com.vscing.model.mapper.MerchantBillMapper;
import com.vscing.model.mapper.MerchantMapper;
import com.vscing.model.mapper.MerchantPriceMapper;
import com.vscing.model.mapper.MovieMapper;
import com.vscing.model.mapper.MovieProducerMapper;
import com.vscing.model.mapper.OrderDetailMapper;
import com.vscing.model.mapper.OrderMapper;
import com.vscing.model.mapper.ShowAreaMapper;
import com.vscing.model.mapper.ShowMapper;
import com.vscing.model.platform.QueryCinema;
import com.vscing.model.platform.QueryCinemaDto;
import com.vscing.model.platform.QueryCinemaShow;
import com.vscing.model.platform.QueryCity;
import com.vscing.model.platform.QueryCityDto;
import com.vscing.model.platform.QueryDistrict;
import com.vscing.model.platform.QueryDto;
import com.vscing.model.platform.QueryMovie;
import com.vscing.model.platform.QueryMovieDto;
import com.vscing.model.platform.QueryMovieProducer;
import com.vscing.model.platform.QueryOrder;
import com.vscing.model.platform.QueryOrderTicket;
import com.vscing.model.platform.QueryOrderTicketDto;
import com.vscing.model.platform.QuerySeat;
import com.vscing.model.platform.QuerySeatDto;
import com.vscing.model.platform.QuerySeatList;
import com.vscing.model.platform.QueryShow;
import com.vscing.model.platform.QueryShowArea;
import com.vscing.model.platform.QueryShowDto;
import com.vscing.model.platform.QuerySubmitOrderDto;
import com.vscing.model.platform.SeatList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlatformServiceImpl implements PlatformService {

  private static final String REDIS_KEY = "platformAddressList";

  @Autowired
  private SupplierServiceFactory supplierServiceFactory;

  @Autowired
  private RedisService redisService;

  @Autowired
  private MerchantMapper merchantMapper;

  @Autowired
  private MerchantPriceMapper merchantPriceMapper;

  @Autowired
  private MerchantBillMapper merchantBillMapper;

  @Autowired
  private CityMapper cityMapper;

  @Autowired
  private DistrictMapper districtMapper;

  @Autowired
  private CinemaMapper cinemaMapper;

  @Autowired
  private MovieMapper movieMapper;

  @Autowired
  private MovieProducerMapper movieProducerMapper;

  @Autowired
  private ShowMapper showMapper;

  @Autowired
  private ShowAreaMapper showAreaMapper;

  @Autowired
  private NotifyService notifyService;

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private OrderDetailMapper orderDetailMapper;

  private Merchant merchant;

  @Override
  public boolean verify(String builderStr, QueryDto record) {
    // 获取当前时间戳
    long currentTimeMillis = System.currentTimeMillis();
    // 假设允许的时间差为1分钟（60000毫秒）
    long allowedTimeDifference = 60000;
    // 比较时间戳
    if (Math.abs(currentTimeMillis - record.getTimestamp()) > allowedTimeDifference) {
      return false;
    }
    // 验证签名
    Merchant merchant = merchantMapper.selectById(record.getUserId());
    this.merchant = merchant;
    String vSign = SignatureGenerator.md5(builderStr + "&key=" + merchant.getSecretKey()).toUpperCase();
    return vSign.equals(record.getSign());
  }

  @Override
  public List<QueryCity> city(QueryCityDto record) {

    List<QueryCity> cityList;

    if(redisService.hasKey(REDIS_KEY)) {
      // 使用自定义工具类
      String redisValue = (String) redisService.get(REDIS_KEY);
      cityList = JsonUtils.parseObject(redisValue, new TypeReference<List<QueryCity>>() {});
    } else {
      cityList = Optional.ofNullable(cityMapper.getPlatformList())
          .orElse(Collections.emptyList())
          .stream()
          .toList();

      List<QueryDistrict> districtList = Optional.ofNullable(districtMapper.getPlatformList())
          .orElse(Collections.emptyList())
          .stream()
          .toList();

      for (QueryCity city : cityList) {
        city.setDistrictList(districtList.stream()
            .filter(district -> district.getCityId().equals(city.getCityId()))
            .toList());
      }

      // 以字符串形式存入
      redisService.set(REDIS_KEY, JsonUtils.toJsonString(cityList));
    }

    return cityList;
  }

  @Override
  public List<QueryCinema> cinema(QueryCinemaDto record) {
    List<QueryCinema> list = cinemaMapper.getPlatformList(record);
    return CollectionUtil.isEmpty(list) ? Collections.emptyList() : list;
  }

  @Override
  public List<QueryMovie> movie(QueryMovieDto record) {
    List<QueryMovie> movieList = movieMapper.getPlatformList(record);
    movieList = CollectionUtil.isEmpty(movieList) ? Collections.emptyList() : movieList;
    List<QueryMovieProducer> movieProducerList = movieProducerMapper.getPlatformList(record);
    movieProducerList = CollectionUtil.isEmpty(movieProducerList) ? Collections.emptyList() : movieProducerList;
    for (QueryMovie movie : movieList) {
      movie.setMovieProducerList(movieProducerList.stream()
          .filter(movieProducer -> movieProducer.getMovieId().equals(movie.getMovieId()))
          .toList());
    }
    return movieList;
  }

  @Override
  public QueryCinemaShow show(QueryShowDto record) {
    // 获取影院信息
    QueryCinemaShow cinemaShow = cinemaMapper.getPlatformInfo(record.getCinemaId());
    if (cinemaShow == null) {
      return null;
    }
    // 商户详情
    Merchant merchant = this.merchant;
    // 商户加价详情
    MerchantPrice merchantPrice = merchantPriceMapper.getPlatformInfo(merchant.getId());
    // 加价
    BigDecimal markupAmount = merchantPrice.getMarkupAmount();
    // 获取影院场次列表
    List<QueryShow> showList = showMapper.getPlatformList(record);
    // 获取影院场次id集合
    List<Long> showIds = showList.stream().map(QueryShow::getShowId).toList();
    List<QueryShowArea> showAreaList = showAreaMapper.getPlatformShowIds(showIds);
    for (QueryShow show : showList) {
      // 售价
      BigDecimal newShowPrice = show.getShowPrice();
      if(newShowPrice != null && markupAmount != null) {
        show.setShowPrice(newShowPrice.add(markupAmount));
      }
      // 结算价
      BigDecimal newUserPrice = show.getUserPrice();
      if(newUserPrice != null && markupAmount != null) {
        show.setUserPrice(newUserPrice.add(markupAmount));
      }
      // 场次价格
      show.setShowAreaList(showAreaList.stream()
          .filter(showArea -> showArea.getShowId().equals(show.getShowId()))
          .peek(showArea -> {
            // 售价
            if (showArea.getUserPrice() != null && markupAmount != null) {
              showArea.setUserPrice(showArea.getUserPrice().add(markupAmount));
            }
            // 结算价
            if (showArea.getShowPrice() != null && markupAmount != null) {
              showArea.setShowPrice(showArea.getShowPrice().add(markupAmount));
            }
          })
          .toList());
    }
    cinemaShow.setShowList(showList);
    return cinemaShow;
  }

  @Override
  public QuerySeat seat(QuerySeatDto record) {
    try {
      // 结果集
      QuerySeat querySeat = new QuerySeat();
      // 初始化
      List<QuerySeatList> seatList = new ArrayList<>();
      // 查询场次信息
      Show show = showMapper.getPlatformInfo(record.getShowId());
      if(show == null) {
        throw new ServiceException("场次信息错误");
      }
      // 准备请求参数
      Map<String, String> params = new HashMap<>();
      params.put("showId", show.getTpShowId());
      params.put("addFlag", String.valueOf(record.getAddFlag()));
      // 发送请求并获取响应
      SupplierService supplierService = supplierServiceFactory.getSupplierService("jfshou");
      String responseBody = supplierService.sendRequest("/seat/query", params);

      ObjectMapper objectMapper = JsonUtils.getObjectMapper();
      Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
      Integer code = (Integer) responseMap.get("code");
      if(code != ResultCode.SUCCESS.getCode()) {
        throw new ServiceException("获取座位图失败");
      }
      Map<String, Object> data = (Map<String, Object>) responseMap.get("data");
      Integer restrictions = (Integer) data.get("restrictions");
      List<Map<String, Object>> seatListRaw = (List<Map<String, Object>>) data.get("seats");
      // 遍历座位列表并解析 seatNo
      if (seatListRaw != null && !seatListRaw.isEmpty()) {
        for (Map<String, Object> seatRaw : seatListRaw) {
          QuerySeatList querySeatList = new QuerySeatList();
          querySeatList.setColumnNo((String) seatRaw.get("columnNo"));
          querySeatList.setRowNo((String) seatRaw.get("rowNo"));
          querySeatList.setSeatNo((String) seatRaw.get("seatNo"));
          querySeatList.setStatus((String) seatRaw.get("status"));
          querySeatList.setLoveStatus((Integer) seatRaw.get("loveStatus"));
          querySeatList.setSeatId((String) seatRaw.get("seatId"));
          querySeatList.setAreaId((String) seatRaw.get("areaId"));
          seatList.add(querySeatList);
        }
      }
      // 设置返回结果
      querySeat.setRestrictions(restrictions);
      querySeat.setSeatList(seatList);
      return querySeat;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public QueryOrder submitOrder(QuerySubmitOrderDto record) {
    try {
      QueryOrder queryOrder = new QueryOrder();
      // 查询场次信息
      Show show = showMapper.getPlatformInfo(record.getShowId());
      if(show == null) {
        throw new ServiceException("场次信息错误");
      }
      // 商户详情
      Merchant merchant = this.merchant;
      // 商户加价详情
      MerchantPrice merchantPrice = merchantPriceMapper.getPlatformInfo(merchant.getId());
      // 加价
      BigDecimal markupAmount = merchantPrice.getMarkupAmount();
      // 获取区域ID
      List<String> areas = record.getSeatList().stream()
          // 提取areaId
          .map(SeatList::getAreaId)
          // 过滤掉null
          .filter(Objects::nonNull)
          // 过滤掉空字符串
          .filter(areaId -> !areaId.trim().isEmpty())
          .toList();
      // 构建区域价格映射
      Map<String, ShowArea> areaPriceMap = new HashMap<>(0);
      // 计算价格
      if(!areas.isEmpty()) {
        // 获取区域价格
        List<ShowArea> showAreaList = showAreaMapper.selectByShowIdAreas(record.getShowId(), areas);
        // 构建区域价格映射
        areaPriceMap = showAreaList.stream()
            .collect(Collectors.toMap(
                ShowArea::getArea,
                area -> area,
                // 处理重复键的情况（如果有的话）
                (existing, replacement) -> existing
            ));
      }
      // 生成订单ID
      Long orderId = IdUtil.getSnowflakeNextId();
      // 订单号
      String orderSn = OrderUtils.generateOrderSn("HY", 1);
      // 计算订单总价格
      BigDecimal totalPrice = BigDecimal.ZERO;
      // 订单官方价
      BigDecimal officialPrice = BigDecimal.ZERO;
      // 订单结算价
      BigDecimal settlementPrice = BigDecimal.ZERO;
      // 座位信息
      List<String> seatInfo = new ArrayList<>();
      // 遍历区域ID，填充最终结果
      List<OrderDetail> orderDetailList = new ArrayList<>();
      // 循环座位数据
      for (SeatList seatList : record.getSeatList()) {
        ShowArea areaPrice = areaPriceMap.get(seatList.getAreaId());
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
        BigDecimal price = userPrice.add(markupAmount);
        // 订单总价
        totalPrice = totalPrice.add(price);
        officialPrice = officialPrice.add(showPrice);
        settlementPrice = settlementPrice.add(userPrice);
        // 座位
        seatInfo.add(seatList.getSeatName());
        // 订单详情
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(IdUtil.getSnowflakeNextId());
        orderDetail.setOrderId(orderId);
        orderDetail.setTpAreaId(seatList.getAreaId());
        orderDetail.setTpSeatId(seatList.getSeatId());
        orderDetail.setTpSeatName(seatList.getSeatName());
        orderDetail.setTotalPrice(price);
        orderDetail.setOfficialPrice(showPrice);
        orderDetail.setSettlementPrice(userPrice);
        orderDetailList.add(orderDetail);
      }
      // 判断商户余额是否足够支付
      if(totalPrice.compareTo(merchant.getBalance()) > 0) {
        throw new ServiceException("余额不足");
      }
      int rowsAffected = 0;
      // 创建订单数据
      Order order = new Order();
      order.setId(orderId);
      order.setSupplierId(show.getSupplierId());
      order.setCinemaId(show.getCinemaId());
      order.setMovieId(show.getMovieId());
      order.setShowId(show.getId());
      // 商户id
      order.setMerchantId(merchant.getId());
      // 手机号
      order.setPhone(record.getTakePhoneNumber());
      // 状态
      order.setStatus(2);
      // 支付状态
      order.setPaymentStatus(2);
      // 下单方式
      order.setOrderType(3);
      // 下单平台
      order.setPlatform(50);
      // 数量
      order.setPurchaseQuantity(record.getSeatList().size());
      // 金额
      order.setTotalPrice(totalPrice);
      order.setOfficialPrice(officialPrice);
      order.setSettlementPrice(settlementPrice);
      // 场次信息
      order.setHallName(show.getHallName());
      // 座位信息
      String seatInfoStr = String.join(",", seatInfo);
      order.setSeatInfo(seatInfoStr);
      // 是否允许调座
      order.setSeatAdjusted(record.getSupportChangeSeat());
      // 场次结束时间
      order.setStopShowTime(show.getShowTime().plusMinutes(show.getDuration()));
      // 订单号
      order.setOrderSn(orderSn);
      // 商户订单号
      order.setExtOrderSn(record.getTradeNo());
      // 开始创建
      rowsAffected = orderMapper.insert(order);
      if (rowsAffected <= 0) {
        throw new ServiceException("创建订单失败");
      }
      // 创建订单详情数据
      if(!orderDetailList.isEmpty()) {
        rowsAffected = orderDetailMapper.apiBatchInsert(orderDetailList);
        if (rowsAffected != orderDetailList.size()) {
          throw new ServiceException("创建订单详情数据失败");
        }
      }
      // 商户扣款
      merchant.setBalance(merchant.getBalance().subtract(totalPrice));
      merchant.setVersion(merchant.getVersion());
      rowsAffected = merchantMapper.updateVersion(merchant);
      if (rowsAffected != orderDetailList.size()) {
        throw new ServiceException("商户扣款失败");
      }
      // 商户账单新增数据
      MerchantBill merchantBill = new MerchantBill();
      merchantBill.setId(IdUtil.getSnowflakeNextId());
      merchantBill.setMerchantId(merchant.getId());
      merchantBill.setPlatformOrderNo(orderSn);
      merchantBill.setExternalOrderNo(record.getTradeNo());
      merchantBill.setChangeType(1);
      merchantBill.setChangeAmount(totalPrice);
      BigDecimal newBalance = merchant.getBalance();
      newBalance = newBalance.subtract(totalPrice);
      merchantBill.setChangeAfterBalance(newBalance);
      merchantBill.setStatus(2);
      rowsAffected = merchantBillMapper.insert(merchantBill);
      if (rowsAffected != orderDetailList.size()) {
        throw new ServiceException("创建商户账单失败");
      }
      // 返回平台订单号、商户订单号、下单时间、取票手机号
      queryOrder.setOrderNo(orderSn);
      queryOrder.setTradeNo(record.getTradeNo());
      queryOrder.setOrderTime(LocalDateTime.now());
      queryOrder.setPhoneNumber(record.getTakePhoneNumber());
      // 异步通知mq
      notifyService.ticketOrder(orderSn);
      return queryOrder;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public QueryOrderTicket orderTicket(QueryOrderTicketDto record) {
    return orderMapper.getPlatformInfo(record);
  }

}
