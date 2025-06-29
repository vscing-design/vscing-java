package com.vscing.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.OrderService;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.RedisService;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.common.service.supplier.SupplierService;
import com.vscing.common.service.supplier.SupplierServiceFactory;
import com.vscing.common.utils.JsonUtils;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.common.utils.OrderUtils;
import com.vscing.model.dto.OrderListDto;
import com.vscing.model.dto.PricingRuleListDto;
import com.vscing.model.dto.SeatListDto;
import com.vscing.model.dto.ShowInforDto;
import com.vscing.model.entity.*;
import com.vscing.model.enums.AppletTypeEnum;
import com.vscing.model.enums.JfshouOrderSubmitResponseCodeEnum;
import com.vscing.model.http.HttpOrder;
import com.vscing.model.http.HttpTicketCode;
import com.vscing.model.mapper.*;
import com.vscing.model.mq.SyncCodeMq;
import com.vscing.model.request.OrderChangeRequest;
import com.vscing.model.request.OrderSaveRequest;
import com.vscing.model.utils.PricingUtil;
import com.vscing.model.vo.OrderDetailVo;
import com.vscing.model.vo.OrderPriceVo;
import com.vscing.model.vo.OrderVo;
import com.vscing.mq.config.DelayRabbitMQConfig;
import com.vscing.mq.service.RabbitMQService;
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
 * @date 2024/12/31 01:30
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private SupplierServiceFactory supplierServiceFactory;

  @Autowired
  private AppletServiceFactory appletServiceFactory;

  @Autowired
  private RedisService redisService;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private OrderDetailMapper orderDetailMapper;

  @Autowired
  private ShowMapper showMapper;

  @Autowired
  private CouponMapper couponMapper;

  @Autowired
  private ShowAreaMapper showAreaMapper;

  @Autowired
  private PricingRuleMapper pricingRuleMapper;

  @Autowired
  private MerchantMapper merchantMapper;

  @Autowired
  private MerchantBillMapper merchantBillMapper;

  @Autowired
  private RabbitMQService rabbitMQService;

  @Override
  public List<OrderVo> getList(OrderListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return orderMapper.getList(data);
  }

  @Override
  public OrderDetailVo getDetails(Long id) {
    OrderDetailVo orderSave = orderMapper.selectEditById(id);
    if (ObjectUtil.isNull(orderSave)) {
      return null;
    }

    // 查询座位列表，并初始化为空列表以防查询结果为 null
    List<SeatListDto> seatList = CollUtil.isEmpty(orderDetailMapper.selectByOrderId(id))
        ? CollUtil.newArrayList() : orderDetailMapper.selectByOrderId(id);

    // 设置座位列表到 OrderSaveRequest 对象中
    orderSave.setSeatList(seatList);

    return orderSave;
  }

  @Override
  public OrderPriceVo getOrderPrice(OrderListDto data){
    return orderMapper.getOrderPrice(data);
  }

  @Override
  public boolean cancelOrder(Long id, Long by) {
    Order orderInfo = orderMapper.selectById(id);
    // 判断订单状态
    if (orderInfo == null || orderInfo.getStatus() >= 5) {
      return false;
    }
    // 修改订单状态
    Order order = new Order();
    order.setId(id);
    order.setStatus(5);
    order.setUpdatedBy(by);
    int rowsAffected = orderMapper.update(order);
    return rowsAffected > 0;
  }

  @Override
  public boolean refundOrder(Long id, Long by) {
    Order orderInfo = orderMapper.selectById(id);
    // 判断订单状态
    if (orderInfo == null || orderInfo.getStatus() >= 6) {
      return false;
    }
    // 获取支付句柄
    String appletType = AppletTypeEnum.findByCode(orderInfo.getPlatform());
    AppletService appletService = appletServiceFactory.getAppletService(appletType);
    // 修改订单状态
    Order order = new Order();
    order.setId(id);
    order.setStatus(6);
    order.setUpdatedBy(by);
    // 组装参数
    Map<String, Object> refundData = new HashMap<>(4);
    refundData.put("outTradeNo", order.getOrderSn());
    refundData.put("tradeNo", order.getTradeNo());
    refundData.put("refundNo", order.getRefundNo());
    refundData.put("totalAmount", order.getTotalPrice());
    refundData.put("baiduUserId", order.getBaiduUserId());
    // 发送退款请求
    boolean res = appletService.refundOrder(refundData);
    log.info("退款结果: {}", res);
    // 处理退款结果
    if (res) {
      order.setStatus(7);
      // 查看是否有优惠券
      if(order.getCouponId() != null) {
        Coupon coupon = new Coupon();
        coupon.setId(order.getCouponId());
        coupon.setStatus(1);
        coupon.setVerifyAt(null);
        couponMapper.updateStatus(coupon);
      }
    } else {
      order.setStatus(8);
    }
    int rowsAffected = orderMapper.update(order);
    return rowsAffected > 0;
  }

  @Override
  public boolean verifyOrderSeat(OrderSaveRequest orderSave) {
    // 获取座位ID
    List<String> seatIds = orderSave.getSeatList().stream()
        // 提取seatId
        .map(SeatListDto::getSeatId)
        // 过滤掉null
        .filter(Objects::nonNull)
        // 过滤掉空字符串
        .filter(seatId -> !seatId.trim().isEmpty())
        .collect(Collectors.toList());
    return orderMapper.checkOrderShowSeat(orderSave.getShowId(), seatIds);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public boolean createOrder(OrderSaveRequest orderSave, Long by) {
    // 获取场次ID
    Long showId = orderSave.getShowId();
    // 获取场次全局价格
    Show show = showMapper.selectById(showId);
    // 获取结算规则列表
    List<PricingRule> pricingRules = pricingRuleMapper.getList(new PricingRuleListDto());
    if(show == null || pricingRules == null) {
      return false;
    }
    // 获取区域ID
    List<String> areas = orderSave.getSeatList().stream()
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
    if(!areas.isEmpty()) {
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
    // 计算订单总价格
    BigDecimal totalPrice = BigDecimal.ZERO;
    BigDecimal officialPrice = BigDecimal.ZERO;
    BigDecimal settlementPrice = BigDecimal.ZERO;
    List<String> seatInfo = new ArrayList<>();
    // 遍历区域ID，填充最终结果
    List<OrderDetail> orderDetailList = new ArrayList<>();
    // 循环座位数据
    for (SeatListDto seatList : orderSave.getSeatList()) {
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
      // 订单官方价
      officialPrice = officialPrice.add(showPrice);
      // 订单结算价
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
      orderDetail.setCreatedBy(by);
      orderDetailList.add(orderDetail);
    }

    // 创建数据
    try {
      // 创建用户
      Long userId = IdUtil.getSnowflakeNextId();
      User userInfo = userMapper.selectByPhone(orderSave.getPhone());
      int rowsAffected = 0;
      if(userInfo == null) {
        User user = new User();
        user.setId(userId);
        user.setSource(orderSave.getUserSource());
        String username = orderSave.getUsername();
        if (username == null || username.trim().isEmpty()) {
          // 如果用户名为空或仅包含空白字符，则生成随机昵称
          username = "HY_" + RandomUtil.randomString(8);
        }
        user.setUsername(username);
        user.setCreatedBy(by);
        user.setPhone(orderSave.getPhone());
        rowsAffected = userMapper.insert(user);
        if (rowsAffected <= 0) {
          throw new ServiceException("创建用户失败");
        }
      } else {
        userId = userInfo.getId();
      }

      // 创建订单数据
      Order order = new Order();
      order.setId(orderId);
      order.setUserId(userId);
      order.setSupplierId(1869799230973227008L);
      order.setCinemaId(orderSave.getCinemaId());
      order.setMovieId(orderSave.getMovieId());
      order.setShowId(orderSave.getShowId());
      // 手机号
      order.setPhone(orderSave.getPhone());
      // 状态
      order.setStatus(orderSave.getStatus());
      // 下单方式
      order.setOrderType(2);
      // 下单平台
      order.setPlatform(orderSave.getUserSource());
      // 数量金额
      order.setPurchaseQuantity(orderSave.getSeatList().size());
      order.setTotalPrice(totalPrice);
      order.setOfficialPrice(officialPrice);
      order.setSettlementPrice(settlementPrice);
      // 场次信息
      order.setHallName(show.getHallName());
      // 座位信息
      String seatInfoStr = String.join(",", seatInfo);
      order.setSeatInfo(seatInfoStr);
      // 备注信息
      order.setMemo(orderSave.getMemo());
      // 订单号
      String orderSn = OrderUtils.generateOrderSn("HY", 1);
      order.setOrderSn(orderSn);
      order.setCreatedBy(by);
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
      throw new ServiceException(e.getMessage());
    }
    return true;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public boolean changeOrder(OrderChangeRequest orderChange, Long by) {
    // 获取场次ID
    Long orderId = orderChange.getId();
    Order orderData = orderMapper.selectById(orderId);
    if(orderData == null) {
      return false;
    }
    // 获取场次ID
    Long showId = orderData.getShowId();
    // 获取场次全局价格
    Show show = showMapper.selectById(showId);
    // 获取结算规则列表
    List<PricingRule> pricingRules = pricingRuleMapper.getList(new PricingRuleListDto());
    if(show == null || pricingRules == null) {
      return false;
    }
    // 获取区域ID
    List<String> areas = orderChange.getSeatList().stream()
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
    // 计算订单总价格
    BigDecimal totalPrice = BigDecimal.ZERO;
    BigDecimal officialPrice = BigDecimal.ZERO;
    BigDecimal settlementPrice = BigDecimal.ZERO;
    List<String> seatInfo = new ArrayList<>();
    // 遍历区域ID，填充最终结果
    List<OrderDetail> orderDetailList = new ArrayList<>();
    // 循环座位数据
    for (SeatListDto seatList : orderChange.getSeatList()) {
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
      orderDetail.setCreatedBy(by);
      orderDetailList.add(orderDetail);
    }
    // 创建数据
    try {
      int rowsAffected = 0;
      // 删除订单详情数据
      rowsAffected = orderDetailMapper.softDeleteByOrderId(orderId, by);
      if (rowsAffected <= 0) {
        throw new ServiceException("删除订单详情失败");
      }
      // 创建订单数据
      Order order = new Order();
      order.setId(orderId);
      // 数量金额
      order.setPurchaseQuantity(orderChange.getSeatList().size());
      order.setTotalPrice(totalPrice);
      order.setOfficialPrice(officialPrice);
      order.setSettlementPrice(settlementPrice);
      // 座位信息
      String seatInfoStr = seatInfo.stream()
          .collect(Collectors.joining(","));
      order.setSeatInfo(seatInfoStr);
      order.setUpdatedBy(by);
      // 调座
      order.setIsAdjusted(1);
      // 开始创建
      rowsAffected = orderMapper.update(order);
      if (rowsAffected <= 0) {
        throw new ServiceException("编辑订单失败");
      }
      // 创建订单详情数据
      if(!orderDetailList.isEmpty()) {
        rowsAffected = orderDetailMapper.batchInsert(orderDetailList);
        if (rowsAffected != orderDetailList.size()) {
          throw new ServiceException("创建订单详情数据失败");
        }
      }
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
    return true;
  }

  @Override
  public boolean ticketOrder(Long id, Long by) {
    try {
      // 订单详情
      Order order = orderMapper.selectById(id);
      if(order == null || order.getStatus() != 2) {
        throw new ServiceException("订单数据不存在");
      }
      // 场次详情
      Show show = showMapper.selectById(order.getShowId());
      if(show == null) {
        throw new ServiceException("场次数据不存在");
      }
      // 获取订单详情
      List<SeatListDto> seatList = orderDetailMapper.selectByOrderId(id);
      if(seatList == null || seatList.isEmpty()) {
        throw new ServiceException("订单详情数据不存在");
      }
      List<ShowInforDto> showInfor = MapstructUtils.convert(seatList, ShowInforDto.class);
      // 判断是否需要先改变订单状态
      Order updateOrder = new Order();
      updateOrder.setId(order.getId());
      if(order.getStatus() != 3) {
        // 改变订单状态
        updateOrder.setStatus(3);
        updateOrder.setUpdatedBy(by);
        int res = orderMapper.update(updateOrder);
        if (res <= 0) {
          throw new ServiceException("改变订单状态失败");
        }
      }
      // 将 List 转换为 JSON 字符串
      String showInforStr = JSONUtil.toJsonStr(showInfor);
      // 准备请求参数
      Map<String, String> params = new HashMap<>();
      params.put("showId", show.getTpShowId());
      params.put("showInfor", showInforStr);
      params.put("notifyUrl", "https://sys-api.hiyaflix.cn/v1/notify/order");
      params.put("takePhoneNumber", order.getPhone());
      params.put("tradeNo", order.getOrderSn());
      params.put("supportChangeSeat", "1");
      params.put("addFlag", "1");
      SupplierService supplierService = supplierServiceFactory.getSupplierService("jfshou");
      // 发送请求并获取响应
      String responseBody = supplierService.sendRequest("/order/preferential/submit", params);
      // 将 JSON 字符串解析为 JsonNode 对象
      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
      Integer code = (Integer) responseMap.getOrDefault("code", 0);
      String message = (String) responseMap.getOrDefault("message", "未知错误");
      // 保存三方接口的返回结果
      updateOrder.setResponseBody(responseBody);
      // 调用保存
      orderMapper.update(updateOrder);
      // 判断出票是否异常
      if(!JfshouOrderSubmitResponseCodeEnum.isErrorCode(code)) {
        // 发送mq异步处理 同步出票信息
        SyncCodeMq syncCodeMq = new SyncCodeMq();
        syncCodeMq.setOrderId(order.getId());
        syncCodeMq.setNum(1);
        String msg = JsonUtils.toJsonString(syncCodeMq);
        rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.SYNC_CODE_ROUTING_KEY, msg, 3*60*1000);
        return true;
      }
      throw new ServiceException(message);
    } catch (Exception e) {
      log.error("调用三方下单失败", e);
    }
    return false;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public boolean supplierOrder(HttpOrder httpOrder) {

    try {
      int rowsAffected = 0;
      // 根据订单号获取订单
      Order order = orderMapper.selectByOrderSn(httpOrder.getTradeNo());
      if(order == null) {
        throw new ServiceException("订单数据不存在");
      }
      // 订单取票码
      List<HttpTicketCode> ticketCodeList = httpOrder.getTicketCode();
      if(ticketCodeList == null || ticketCodeList.isEmpty()) {
        throw new ServiceException("订单取票码不存在");
      }
      // 改变订单状态
      Order updateOrder = new Order();
      updateOrder.setId(order.getId());
      updateOrder.setStatus(4);
      updateOrder.setSupplierOrderSn(httpOrder.getOrderNo());
      updateOrder.setTicketCode(JsonUtils.toJsonString(ticketCodeList));
      rowsAffected = orderMapper.update(updateOrder);
      if (rowsAffected <= 0) {
        throw new ServiceException("改变订单状态失败");
      }
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
    return true;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public void closeOrder(Long id, Long by) {
    try {
      int rowsAffected = 0;
      // 根据订单id获取订单
      Order order = orderMapper.selectById(id);
      if(order == null || order.getMerchantId() == null || order.getStatus() > 4) {
        throw new ServiceException("订单数据异常");
      }
      // 根据商户id查询商户信息
      Merchant merchant = merchantMapper.selectById(order.getMerchantId());
      // 修改订单状态到取消
      Order updateOrder = new Order();
      updateOrder.setId(order.getId());
      updateOrder.setStatus(5);
      rowsAffected = orderMapper.update(updateOrder);
      if (rowsAffected <= 0) {
        throw new ServiceException("改变订单状态失败");
      }
      // 计算商户余额
      BigDecimal merchantBalance = merchant.getBalance();
      merchantBalance = merchantBalance.add(order.getTotalPrice());
      // 创建商户账单
      MerchantBill merchantBill = new MerchantBill();
      merchantBill.setId(IdUtil.getSnowflakeNextId());
      merchantBill.setMerchantId(order.getMerchantId());
      merchantBill.setPlatformOrderNo(order.getOrderSn());
      merchantBill.setExternalOrderNo(order.getExtOrderSn());
      merchantBill.setChangeAmount(order.getTotalPrice());
      merchantBill.setChangeAfterBalance(merchantBalance);
      merchantBill.setStatus(2);
      merchantBill.setChangeType(2);
      merchantBill.setProductType(1);
      rowsAffected = merchantBillMapper.insert(merchantBill);
      if (rowsAffected < 0) {
        throw new ServiceException("创建商户账单失败");
      }
      // 商户余额修改
      merchant.setBalance(merchantBalance);
      merchant.setVersion(merchant.getVersion());
      rowsAffected = merchantMapper.updateVersion(merchant);
      if (rowsAffected < 0) {
        throw new ServiceException("商户扣款失败");
      }
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
  }

}
