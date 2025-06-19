package com.vscing.api.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.api.service.NotifyService;
import com.vscing.api.service.PlatformVipGoodsService;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.utils.JsonUtils;
import com.vscing.common.utils.OrderUtils;
import com.vscing.model.dto.MerchantPriceVipGoodsDto;
import com.vscing.model.entity.Merchant;
import com.vscing.model.entity.MerchantBill;
import com.vscing.model.entity.MerchantPrice;
import com.vscing.model.entity.VipGoods;
import com.vscing.model.entity.VipOrder;
import com.vscing.model.mapper.MerchantBillMapper;
import com.vscing.model.mapper.MerchantMapper;
import com.vscing.model.mapper.MerchantPriceMapper;
import com.vscing.model.mapper.VipGoodsAttachMapper;
import com.vscing.model.mapper.VipGoodsMapper;
import com.vscing.model.mapper.VipGroupMapper;
import com.vscing.model.mapper.VipOrderMapper;
import com.vscing.model.mq.OrderNotifyMq;
import com.vscing.model.platform.QueryOrder;
import com.vscing.model.platform.QueryOrderTicketDto;
import com.vscing.model.platform.QuerySubmitVipOrderDto;
import com.vscing.model.platform.QueryVipGoods;
import com.vscing.model.platform.QueryVipGoodsAttach;
import com.vscing.model.platform.QueryVipGoodsDetails;
import com.vscing.model.platform.QueryVipGoodsDetailsDto;
import com.vscing.model.platform.QueryVipGoodsDto;
import com.vscing.model.platform.QueryVipGroup;
import com.vscing.model.platform.QueryVipGroupDto;
import com.vscing.model.platform.QueryVipOrderTicket;
import com.vscing.mq.config.DelayRabbitMQConfig;
import com.vscing.mq.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlatformVipGoodsServiceImpl implements PlatformVipGoodsService {

  @Autowired
  private VipGroupMapper vipGroupMapper;

  @Autowired
  private VipGoodsMapper vipGoodsMapper;

  @Autowired
  private VipGoodsAttachMapper vipGoodsAttachMapper;

  @Autowired
  private VipOrderMapper vipOrderMapper;

  @Autowired
  private MerchantPriceMapper merchantPriceMapper;

  @Autowired
  private MerchantMapper merchantMapper;

  @Autowired
  private MerchantBillMapper merchantBillMapper;

  @Autowired
  private NotifyService notifyService;

  @Autowired
  private RabbitMQService rabbitMQService;

  @Override
  public List<QueryVipGroup> group(QueryVipGroupDto record) {
    PageHelper.startPage(record.getPageNum(), record.getPageSize());
    return vipGroupMapper.getPlatformList(record);
  }

  @Override
  public List<QueryVipGoods> all(QueryVipGoodsDto record) {
    // 分页查询列表
    PageHelper.startPage(record.getPageNum(), record.getPageSize());
    List<QueryVipGoods> list = vipGoodsMapper.getPlatformList(record);
    // 获取商品id集合
    List<Long> vipGoodsIds = list.stream()
        .map(QueryVipGoods::getGoodsId)
        .toList();
    // 根据商品id集合、商户id
    MerchantPriceVipGoodsDto merchantPriceVipGoodsDto = new MerchantPriceVipGoodsDto();
    merchantPriceVipGoodsDto.setMerchantId(record.getUserId());
    merchantPriceVipGoodsDto.setVipGoodsIds(vipGoodsIds);
    // 获取价格列表
    List<MerchantPrice> priceList = merchantPriceMapper.getVipGoodsMarkupList(merchantPriceVipGoodsDto);
    Map<Long, BigDecimal> priceMap = new HashMap<>();
    for (MerchantPrice price : priceList) {
      priceMap.put(price.getVipGoodsId(), price.getMarkupAmount());
    }
    // 遍历数据列表
    for (QueryVipGoods queryVipGoods : list) {
      // 空值检查
      if (queryVipGoods.getGoodsPrice() != null && priceMap.get(queryVipGoods.getGoodsId()) != null) {
        BigDecimal newGoodsPrice = queryVipGoods.getGoodsPrice().add(priceMap.get(queryVipGoods.getGoodsId()));
        queryVipGoods.setGoodsPrice(newGoodsPrice);
      }
    }
    return list;
  }

  @Override
  public QueryVipGoodsDetails details(QueryVipGoodsDetailsDto record) {
    QueryVipGoodsDetails data = vipGoodsMapper.getPlatformDetails(record);
    if(data != null) {
      // 获取价格列表
      MerchantPrice merchantPrice = merchantPriceMapper.getPlatformVipGoods(record.getUserId(), data.getGoodsId());
      // 空值检查
      if (data.getGoodsPrice() != null && merchantPrice != null) {
        BigDecimal newGoodsPrice = data.getGoodsPrice().add(merchantPrice.getMarkupAmount());
        data.setGoodsPrice(newGoodsPrice);
      }
      // 扩展属性
      List<QueryVipGoodsAttach> attachList = vipGoodsAttachMapper.getPlatformByGoodsId(record);
      data.setAttachList(attachList);
    }
    return data;
  }

  @Override
  public QueryOrder submitOrder(QuerySubmitVipOrderDto record, Merchant merchant) {
    try {
      QueryOrder queryOrder = new QueryOrder();
      // 查询商品信息
      VipGoods vipGoods = vipGoodsMapper.selectById(record.getGoodsId());
      if(vipGoods == null) {
        throw new ServiceException("商品信息错误");
      }
      // 加价
      BigDecimal markupAmount = BigDecimal.ZERO;
      // 获取价格列表
      MerchantPrice merchantPrice = merchantPriceMapper.getPlatformVipGoods(record.getUserId(), record.getGoodsId());
      if(merchantPrice != null) {
        markupAmount = merchantPrice.getMarkupAmount();
      }
      // 生成订单ID
      Long orderId = IdUtil.getSnowflakeNextId();
      // 订单号
      String orderSn = OrderUtils.generateOrderSn("HY", 1);
      // 计算订单总价格
      BigDecimal totalPrice = vipGoods.getGoodsPrice().add(markupAmount);
      totalPrice = totalPrice.multiply(BigDecimal.valueOf(record.getBuyNum()));
      // 订单官方价
      BigDecimal officialPrice = vipGoods.getMarketPrice();
      officialPrice = officialPrice.multiply(BigDecimal.valueOf(record.getBuyNum()));
      // 订单结算价
      BigDecimal settlementPrice = vipGoods.getGoodsPrice();
      settlementPrice = settlementPrice.multiply(BigDecimal.valueOf(record.getBuyNum()));
      // 判断商户余额是否足够支付
      if(totalPrice.compareTo(merchant.getBalance()) > 0) {
        throw new ServiceException("余额不足");
      }
      int rowsAffected = 0;
      // 创建订单数据
      VipOrder order = new VipOrder();
      order.setId(orderId);
      order.setSupplierId(vipGoods.getSupplierId());
      // 商户id
      order.setMerchantId(merchant.getId());
      // 手机号
      order.setPhone(record.getPhone());
      // 状态
      order.setStatus(2);
      // 商品信息
      order.setVipGoodsId(record.getGoodsId());
      order.setVipGoodsType(vipGoods.getGoodsType());
      order.setVipGoodsName(vipGoods.getGoodsName());
      order.setMaxMoney(record.getMaxMoney());
      // 数量
      order.setBuyNum(record.getBuyNum());
      // 订单官方价
      order.setOfficialPrice(officialPrice);
      // 订单结算价
      order.setSettlementPrice(settlementPrice);
      // 金额
      order.setTotalPrice(totalPrice);
      // 订单号
      order.setOrderSn(orderSn);
      // 商户订单号
      order.setExtOrderSn(record.getTradeNo());
      // 开始创建
      rowsAffected = vipOrderMapper.insert(order);
      if (rowsAffected <= 0) {
        throw new ServiceException("创建订单失败");
      }
      // 商户余额
      BigDecimal merchantBalance = merchant.getBalance();
      merchantBalance = merchantBalance.subtract(totalPrice);
      // 商户扣款
      merchant.setBalance(merchantBalance);
      merchant.setVersion(merchant.getVersion());
      rowsAffected = merchantMapper.updateVersion(merchant);
      if (rowsAffected < 0) {
        throw new ServiceException("商户扣款失败");
      }
      // 商户账单新增数据
      MerchantBill merchantBill = new MerchantBill();
      merchantBill.setId(IdUtil.getSnowflakeNextId());
      merchantBill.setMerchantId(merchant.getId());
      merchantBill.setProductType(2);
      merchantBill.setPlatformOrderNo(orderSn);
      merchantBill.setExternalOrderNo(record.getTradeNo());
      merchantBill.setChangeType(1);
      merchantBill.setChangeAmount(totalPrice.negate());
      merchantBill.setChangeAfterBalance(merchantBalance);
      merchantBill.setStatus(2);
      rowsAffected = merchantBillMapper.insert(merchantBill);
      if (rowsAffected < 0) {
        throw new ServiceException("创建商户账单失败");
      }
      // 返回平台订单号、商户订单号、下单时间、取票手机号
      queryOrder.setOrderNo(orderSn);
      queryOrder.setTradeNo(record.getTradeNo());
      queryOrder.setOrderTime(LocalDateTime.now());
      queryOrder.setPhoneNumber(record.getPhone());
      // 发起三方下单
      notifyService.ticketVipOrder(orderSn);
      // 发送mq异步处理 2分钟后查询退款订单
      rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.SYNC_VIP_ORDER_ROUTING_KEY, orderId.toString(), 2*60*1000);
      // 发送mq异步处理 5分钟后异步通知
      OrderNotifyMq orderNotifyMq = new OrderNotifyMq();
      orderNotifyMq.setUrl(record.getNotifyUrl());
      orderNotifyMq.setOrderId(orderId);
      orderNotifyMq.setOrderNo(orderSn);
      orderNotifyMq.setOrderType(2);
      orderNotifyMq.setNum(1);
      String msg = JsonUtils.toJsonString(orderNotifyMq);
      rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.ORDER_NOTIFY_ROUTING_KEY, msg, 5*60*1000);
      return queryOrder;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public QueryVipOrderTicket queryOrder(QueryOrderTicketDto record) {
    return vipOrderMapper.getPlatformInfo(record);
  }
}
