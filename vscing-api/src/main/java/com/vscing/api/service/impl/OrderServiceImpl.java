package com.vscing.api.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.api.service.OrderService;
import com.vscing.common.api.ResultCode;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.common.service.supplier.SupplierService;
import com.vscing.common.service.supplier.SupplierServiceFactory;
import com.vscing.model.dto.OrderApiCreatedDto;
import com.vscing.model.dto.OrderApiDetailsDto;
import com.vscing.model.dto.PricingRuleListDto;
import com.vscing.model.dto.SeatListDto;
import com.vscing.model.entity.Order;
import com.vscing.model.entity.OrderDetail;
import com.vscing.model.entity.PricingRule;
import com.vscing.model.entity.Show;
import com.vscing.model.entity.ShowArea;
import com.vscing.model.entity.UserAuth;
import com.vscing.model.mapper.OrderDetailMapper;
import com.vscing.model.mapper.OrderMapper;
import com.vscing.model.mapper.PricingRuleMapper;
import com.vscing.model.mapper.ShowAreaMapper;
import com.vscing.model.mapper.ShowMapper;
import com.vscing.model.mapper.UserAuthMapper;
import com.vscing.model.request.ShowSeatRequest;
import com.vscing.model.utils.PricingUtil;
import com.vscing.model.vo.OrderApiDetailsVo;
import com.vscing.model.vo.OrderApiPaymentVo;
import com.vscing.model.vo.OrderApiSeatListVo;
import com.vscing.model.vo.SeatMapVo;
import com.vscing.model.vo.SeatVo;
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
import java.util.Objects;
import java.util.stream.Collectors;

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
  private AppletServiceFactory appletServiceFactory;

  @Autowired
  private UserAuthMapper userAuthMapper;

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private ShowMapper showMapper;

  @Autowired
  private ShowAreaMapper showAreaMapper;

  @Autowired
  private OrderDetailMapper orderDetailMapper;

  @Autowired
  private PricingRuleMapper pricingRuleMapper;

  /**
   * 生成18位订单编号:8位日期+2位平台号码+2位支付方式+6位以上自增id
   */
  private String generateOrderSn() {

    // 获取当前时间戳，格式为yyyyMMddHHmmssSSS（17位）
    String timestamp = DateUtil.now().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "") + DateUtil.format(DateUtil.date(), "SSS");

    // 生成5位随机数字，用于补足18位
    String randomNum = RandomUtil.randomNumbers(5);

    // 组合生成订单号
    return "HY" + (timestamp + randomNum).substring(0, 18);
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

  @Override
  public boolean verifyOrderSeat(OrderApiDetailsDto orderApiDetails) {
    // 获取座位ID
    List<String> seatIds = orderApiDetails.getSeatList().stream()
        // 提取seatId
        .map(SeatListDto::getSeatId)
        // 过滤掉null
        .filter(Objects::nonNull)
        // 过滤掉空字符串
        .filter(seatId -> !seatId.trim().isEmpty())
        .collect(Collectors.toList());
    return orderMapper.checkOrderShowSeat(orderApiDetails.getShowId(), seatIds);
  }

  @Override
  public OrderApiDetailsVo getDetails(OrderApiDetailsDto orderApiDetails) {
    // 获取结算规则列表
    List<PricingRule> pricingRules = pricingRuleMapper.getList(new PricingRuleListDto());
    // 获取场次全局价格
    OrderApiDetailsVo details = showMapper.selectByOrderDetails(orderApiDetails.getShowId());
    // 获取区域ID
    List<String> areas = orderApiDetails.getSeatList().stream()
        // 提取areaId
        .map(SeatListDto::getAreaId)
        // 过滤掉null
        .filter(Objects::nonNull)
        // 过滤掉空字符串
        .filter(areaId -> !areaId.trim().isEmpty())
        .collect(Collectors.toList());
    // 构建区域价格映射
    Map<String, ShowArea> areaPriceMap = new HashMap<>(0);
    // 计算价格
    if(areas != null && !areas.isEmpty()) {
      // 获取区域价格
      List<ShowArea> showAreaList = showAreaMapper.selectByShowIdAreas(orderApiDetails.getShowId(), areas);
      // 构建区域价格映射
      areaPriceMap = showAreaList.stream()
          .collect(Collectors.toMap(
              ShowArea::getArea,
              area -> area,
              // 处理重复键的情况（如果有的话）
              (existing, replacement) -> existing
          ));
    }
    // 计算订单总价格
    BigDecimal maxPrice = BigDecimal.ZERO;
    BigDecimal mixPrice = BigDecimal.ZERO;
    BigDecimal discount = BigDecimal.ZERO;
    List<OrderApiSeatListVo> orderSeatList = new ArrayList<>();
    // 循环座位数据
    for (SeatListDto seatList : orderApiDetails.getSeatList()) {
      ShowArea areaPrice = areaPriceMap.get(seatList.getAreaId());
      // 场次价格（元）
      BigDecimal showPrice = details.getShowPrice();
      // 影片结算价（元）
      BigDecimal userPrice = details.getUserPrice();
      if (areaPrice != null) {
        // 场次价格（元）
        showPrice = areaPrice.getShowPrice();
        // 影片结算价（元）
        userPrice = areaPrice.getUserPrice();
      }
      // 实际销售价格
      BigDecimal price = PricingUtil.calculateActualPrice(showPrice, userPrice, pricingRules);
      // 优惠金额
      BigDecimal discountPrice = showPrice.subtract(price);
      // 订单总价
      maxPrice = maxPrice.add(showPrice);
      mixPrice = mixPrice.add(price);
      discount = discount.add(discountPrice);
      // 座位列表
      OrderApiSeatListVo orderApiSeatListVo = new OrderApiSeatListVo();
      orderApiSeatListVo.setSeatName(seatList.getSeatName());
      orderApiSeatListVo.setDiscount(discountPrice);
      orderApiSeatListVo.setMaxPrice(showPrice);
      orderApiSeatListVo.setMinPrice(price);
      orderSeatList.add(orderApiSeatListVo);
    }
    // 数据赋值
    details.setShowPrice(null);
    details.setUserPrice(null);
    details.setDiscount(discount);
    details.setMaxPrice(maxPrice);
    details.setMinPrice(mixPrice);
    details.setSeatList(orderSeatList);
    return details;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public OrderApiPaymentVo create(Long userId, OrderApiCreatedDto orderApiCreatedDto) {
    // 支付需要的参数
    String paymentStr;
    // 平台转换
    Integer platform = 0;
    // 处理微信参数
    if(AppletServiceFactory.WECHAT.equals(orderApiCreatedDto.getPlatform())) {
      platform = 1;
    }
    // 处理支付宝参数
    if(AppletServiceFactory.ALIPAY.equals(orderApiCreatedDto.getPlatform())) {
      platform = 2;
    }
    Long showId = orderApiCreatedDto.getShowId();
    // 获取场次全局价格
    Show show = showMapper.selectById(showId);
    // 查询用户的openid
    UserAuth userAuth = userAuthMapper.findOpenid(userId, platform);
    // 获取结算规则列表
    List<PricingRule> pricingRules = pricingRuleMapper.getList(new PricingRuleListDto());
    // 获取区域ID
    List<String> areas = orderApiCreatedDto.getSeatList().stream()
        // 提取areaId
        .map(SeatListDto::getAreaId)
        // 过滤掉null
        .filter(Objects::nonNull)
        // 过滤掉空字符串
        .filter(areaId -> !areaId.trim().isEmpty())
        .collect(Collectors.toList());
    // 构建区域价格映射
    Map<String, ShowArea> areaPriceMap = new HashMap<>(0);
    // 计算价格
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
    // 生成订单ID
    Long orderId = IdUtil.getSnowflakeNextId();
    // 订单号
    String orderSn = this.generateOrderSn();
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
    for (SeatListDto seatList : orderApiCreatedDto.getSeatList()) {
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
      BigDecimal price = PricingUtil.calculateActualPrice(showPrice, userPrice, pricingRules);
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

    try {
      // 发起支付
      AppletService appletService = appletServiceFactory.getAppletService(orderApiCreatedDto.getPlatform());
      Map<String, Object> paymentData = new HashMap<>(3);
      paymentData.put("outTradeNo", orderSn);
      paymentData.put("totalAmount", totalPrice);
      paymentData.put("openid", userAuth.getOpenid());
      paymentStr = appletService.getPayment(paymentData);
      int rowsAffected = 0;
      // 创建订单数据
      Order order = new Order();
      order.setId(orderId);
      order.setUserId(userId);
      order.setSupplierId(show.getSupplierId());
      order.setCinemaId(show.getCinemaId());
      order.setMovieId(show.getMovieId());
      order.setShowId(show.getId());
      // 支付订单号 支付宝直接写入，微信写入交易prepay_id预支付交易会话标识（后期再改成订单号）
      order.setTradeNo(paymentStr);
      // 手机号
      order.setPhone(orderApiCreatedDto.getPhone());
      // 状态
      order.setStatus(1);
      // 下单方式
      order.setOrderType(1);
      // 下单平台
      order.setPlatform(platform);
      // 数量金额
      order.setPurchaseQuantity(orderApiCreatedDto.getSeatList().size());
      order.setTotalPrice(totalPrice);
      order.setOfficialPrice(officialPrice);
      order.setSettlementPrice(settlementPrice);
      // 座位信息
      String seatInfoStr = seatInfo.stream()
          .collect(Collectors.joining(","));
      order.setSeatInfo(seatInfoStr);
      order.setSeatAdjusted(orderApiCreatedDto.getSeatAdjusted());
      // 备注信息
      order.setMemo(orderApiCreatedDto.getMemo());
      // 订单号
      order.setOrderSn(orderSn);
      // 开始创建
      rowsAffected = orderMapper.insert(order);
      if (rowsAffected <= 0) {
        throw new ServiceException("创建订单失败");
      }
      // 创建订单详情数据
      if(!orderDetailList.isEmpty()) {
        rowsAffected = orderDetailMapper.batchInsert(orderDetailList);
        if (rowsAffected != orderDetailList.size()) {
          throw new ServiceException("创建订单详情数据失败");
        }
      }

    } catch (Exception e) {
      log.error("下单异常：", e);
      return null;
    }
    OrderApiPaymentVo orderApiPaymentVo = new OrderApiPaymentVo();
    // 处理微信参数
    if(AppletServiceFactory.WECHAT.equals(orderApiCreatedDto.getPlatform())) {

    }
    // 处理支付宝参数
    if(AppletServiceFactory.ALIPAY.equals(orderApiCreatedDto.getPlatform())) {
      orderApiPaymentVo.setTradeNo(paymentStr);
    }

    return orderApiPaymentVo;
  }

}
