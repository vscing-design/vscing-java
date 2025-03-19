package com.vscing.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.api.service.NotifyService;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.common.service.applet.impl.wechat.AppletProperties;
import com.vscing.common.service.applet.impl.wechat.extend.Transfer;
import com.vscing.common.service.supplier.SupplierService;
import com.vscing.common.service.supplier.SupplierServiceFactory;
import com.vscing.common.utils.JsonUtils;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.common.utils.RequestUtil;
import com.vscing.model.dto.BaiduCreateNotifyDto;
import com.vscing.model.dto.BaiduRefundNotifyDto;
import com.vscing.model.dto.SeatListDto;
import com.vscing.model.dto.ShowInforDto;
import com.vscing.model.entity.Order;
import com.vscing.model.entity.Show;
import com.vscing.model.entity.UserWithdraw;
import com.vscing.model.enums.AppletTypeEnum;
import com.vscing.model.enums.JfshouOrderSubmitResponseCodeEnum;
import com.vscing.model.mapper.OrderDetailMapper;
import com.vscing.model.mapper.OrderMapper;
import com.vscing.model.mapper.ShowMapper;
import com.vscing.model.mapper.UserWithdrawMapper;
import com.vscing.model.mq.RebateMq;
import com.vscing.model.mq.SyncCodeMq;
import com.vscing.mq.config.DelayRabbitMQConfig;
import com.vscing.mq.config.FanoutRabbitMQConfig;
import com.vscing.mq.service.RabbitMQService;
import com.wechat.pay.java.service.payments.model.Transaction;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * NotifyServiceImpl
 *
 * @author vscing
 * @date 2025/1/21 23:03
 */
@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {

  @Autowired
  private SupplierServiceFactory supplierServiceFactory;

  @Autowired
  private AppletServiceFactory appletServiceFactory;

  @Autowired
  private RabbitMQService rabbitMQService;

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private OrderDetailMapper orderDetailMapper;

  @Autowired
  private ShowMapper showMapper;

  @Autowired
  private UserWithdrawMapper userWithdrawMapper;

  @Autowired
  private AppletProperties appletProperties;

  @Override
  public boolean queryAlipayOrder(HttpServletRequest request) {
    try {
      Map requestParams = request.getParameterMap();
      Map<String, String> params = new HashMap<String,String>(30);
      for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
        String name = (String) iter.next();
        String[] values = (String[]) requestParams.get(name);
        String valueStr = "";
        for (int i = 0; i < values.length; i++) {
          valueStr = (i == values.length - 1) ? valueStr + values[i]
              : valueStr + values[i] + ",";
        }
        // 乱码解决，这段代码在出现乱码时使用。
        // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
        params.put(name, valueStr);
      }
      log.info("支付宝异步通知参数: {}", params);
      // 普通公钥模式验签，切记 alipaypublickey 是支付宝的公钥，请去open.alipay.com对应应用下查看。
//      boolean flag = AlipaySignature.rsaCheckV1(params, alipaypublickey, params.get("charset"),"RSA2");
      AppletService appletService = appletServiceFactory.getAppletService("alipay");
      // 公钥证书模式验签
      boolean signVerified = (boolean) appletService.signValidation(params);
      if (!signVerified) {
        throw new ServiceException("支付宝订单验证签名未通过");
      }
      // 查询支付宝订单
      boolean res = appletService.queryOrder(params);
      if (!res) {
        throw new ServiceException("支付宝订单支付未成功");
      }
      String orderSn = params.get("out_trade_no");
      String tradeNo = params.get("trade_no");
      // 修改订单
      int rowsAffected = orderMapper.updateAlipayOrder(orderSn, tradeNo);
      if (rowsAffected <= 0) {
        throw new ServiceException("修改支付宝订单状态失败");
      }
      // 异步出票
      log.info("提交异步出票任务订单号: {}", orderSn);
      ticketOrder(orderSn);
      // 返回结果
      return true;
    } catch (Exception e) {
      log.error("支付宝回调异常", e);
      return false;
    }
  }

  @Override
  public boolean queryWechatOrder(HttpServletRequest request) {
    try {
      // 原始请求体内容
      String requestBody = RequestUtil.getRequestBody(request);
      log.error("微信下单异步通知请求参数: {}", requestBody);
      // 返回结果
      Map<String, String> params = new HashMap<>(6);
      params.put("wechatPaySerial", request.getHeader("Wechatpay-Serial"));
      params.put("wechatpayNonce", request.getHeader("Wechatpay-Nonce"));
      params.put("wechatSignature", request.getHeader("Wechatpay-Signature"));
      params.put("wechatTimestamp", request.getHeader("Wechatpay-Timestamp"));
      params.put("requestBody", requestBody);
      log.error("微信下单异步通知请求参数集合: {}", params);
      // 支付类
      AppletService appletService = appletServiceFactory.getAppletService("wechat");
      // 签名认证
      Transaction transaction = (Transaction) appletService.signValidation(params);
      if (!Objects.nonNull(transaction)) {
        throw new ServiceException("微信订单验证签名未通过");
      }
      log.error("微信下单异步通知数据解密: {}, transaction_id: {}", transaction, transaction.getTransactionId());
      // 查询微信订单
      Map<String, String> queryData = new HashMap<>(10);
      queryData.put("out_trade_no", transaction.getOutTradeNo());
      queryData.put("transaction_id", transaction.getTransactionId());
      boolean res = appletService.queryOrder(queryData);
      if (!res) {
        throw new ServiceException("微信订单支付未成功");
      }
      String orderSn = queryData.get("out_trade_no");
      String tradeNo = queryData.get("transaction_id");
      // 修改订单
      int rowsAffected = orderMapper.updateWechatOrder(orderSn, tradeNo);
      if (rowsAffected <= 0) {
        throw new ServiceException("修改微信订单状态失败");
      }
      // 异步出票
      log.info("提交异步出票任务，订单号: {}", orderSn);
      ticketOrder(orderSn);
      // 返回结果
      return true;
    } catch (Exception e) {
      log.error("微信回调异常", e);
      return false;
    }
  }

  @Override
  public boolean queryWechatTransfer(HttpServletRequest request) {
    try {
      // 原始请求体内容
      String requestBody = RequestUtil.getRequestBody(request);
      log.info("微信转账异步通知请求参数: {}", requestBody);
      // 返回结果
      Map<String, String> params = new HashMap<>(6);
      params.put("wechatPaySerial", request.getHeader("Wechatpay-Serial"));
      params.put("wechatpayNonce", request.getHeader("Wechatpay-Nonce"));
      params.put("wechatSignature", request.getHeader("Wechatpay-Signature"));
      params.put("wechatTimestamp", request.getHeader("Wechatpay-Timestamp"));
      params.put("requestBody", requestBody);
      log.info("微信转账异步通知请求参数集合: {}", params);
      // 支付类
      AppletService appletService = appletServiceFactory.getAppletService("wechat");
      // 签名认证
      Transfer transfer = (Transfer) appletService.signValidation(params);
      if (!Objects.nonNull(transfer)) {
        throw new ServiceException("微信转账异步通知验证签名未通过");
      }
      log.info("微信转账异步通知数据解密: {}", transfer);
      if (transfer.getState() == null || !transfer.getState().equals("SUCCESS")) {
        throw new ServiceException("微信转账异步通知数据解析失败");
      }
      UserWithdraw userWithdraw = userWithdrawMapper.selectByWithdrawSn(transfer.getOutBillNo());
      userWithdraw.setWithdrawStatus(2);
      userWithdraw.setTransferNo(transfer.getTransferBillNo());
      log.info("微信转账更新类 userWithdraw: {}", userWithdraw);
      int rowsAffected = userWithdrawMapper.updateTransfer(userWithdraw);
      if (rowsAffected <= 0) {
        throw new ServiceException("微信转账更新提现表失败");
      }
      return true;
    } catch (Exception e) {
      log.error("微信转账回调异常: {}", e.getMessage());
      return false;
    }
  }

  @Override
  public boolean queryBaiduOrder(BaiduCreateNotifyDto baiduCreateNotifyDto) {
    try {
      log.info("百度下单异步通知请求参数集合: {}", baiduCreateNotifyDto);
      // 支付类
      AppletService appletService = appletServiceFactory.getAppletService("baidu");
      // 签名认证参数
      Map<String, Object> objectMap = BeanUtil.beanToMap(baiduCreateNotifyDto);
      Map<String, String> stringMap = new TreeMap<>(Comparator.naturalOrder());
      for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
        String key = entry.getKey();
        stringMap.put(key, entry.getValue().toString());
      }
      // 验签
      boolean signVerified = (boolean) appletService.signValidation(stringMap);
      if (!signVerified) {
        throw new ServiceException("百度订单验证签名未通过");
      }
      // 查询百度订单
      boolean res = appletService.queryOrder(stringMap);
      if (!res) {
        throw new ServiceException("百度订单支付未成功");
      }
      // 修改订单
      int rowsAffected = orderMapper.updateBaiduOrder(baiduCreateNotifyDto.getTpOrderId(), String.valueOf(baiduCreateNotifyDto.getOrderId()), baiduCreateNotifyDto.getUserId());
      if (rowsAffected <= 0) {
        throw new ServiceException("修改百度订单状态失败");
      }
      // 异步出票
      log.info("提交百度异步出票任务，订单号: {}", baiduCreateNotifyDto.getTpOrderId());
      ticketOrder(baiduCreateNotifyDto.getTpOrderId());
      // 返回结果
      return true;
    } catch (Exception e) {
      log.error("百度回调异常", e);
      return false;
    }
  }

  @Override
  public boolean queryBaiduRefund(BaiduRefundNotifyDto baiduRefundNotifyDto) {
    try {
      log.info("百度退款异步通知请求参数集合: {}", baiduRefundNotifyDto);
      // 查询订单信息
      Order order = orderMapper.selectByOrderSn(baiduRefundNotifyDto.getTpOrderId());
      if(order == null) {
        throw new ServiceException("订单数据不存在");
      }
      // 获取支付句柄
      String appletType = AppletTypeEnum.findByCode(order.getPlatform());
      AppletService appletService = appletServiceFactory.getAppletService(appletType);
      // 签名认证参数
      Map<String, Object> objectMap = BeanUtil.beanToMap(baiduRefundNotifyDto);
      Map<String, String> queryData = new TreeMap<>(Comparator.naturalOrder());
      for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
        String key = entry.getKey();
        queryData.put(key, entry.getValue().toString());
      }
      // 验签
      boolean signVerified = (boolean) appletService.signValidation(queryData);
      if (!signVerified) {
        throw new ServiceException("百度订单验证签名未通过");
      }
      // 发送退款请求
      queryData.put("refundNo", order.getRefundNo());
      boolean res = appletService.queryRefund(queryData);
      log.info("百度退款订单查询结果: {}", res);
      // 处理退款结果
      if (res) {
        order.setStatus(7);
      } else {
        order.setStatus(8);
      }
      return orderMapper.update(order) > 1;
    } catch (Exception e) {
      log.error("百度退款订单回调异常: ", e);
    }
    return false;
  }

  @Async("threadPoolTaskExecutor")
  @Override
  public void ticketOrder(String orderSn) {
    try {
      // 订单详情
      Order order = orderMapper.selectByOrderSn(orderSn);
      if(order == null || order.getStatus() != 2) {
        throw new ServiceException("订单数据不存在");
      }
      // 场次详情
      Show show = showMapper.selectById(order.getShowId());
      if(show == null) {
        throw new ServiceException("场次数据不存在");
      }
      // 获取订单详情
      List<SeatListDto> seatList = orderDetailMapper.selectByOrderId(order.getId());
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
        updateOrder.setUpdatedBy(0L);
        int res = orderMapper.update(updateOrder);
        if (res <= 0) {
          throw new ServiceException("改变订单状态失败");
        }
      }
      // 黑名单测试
      if("15901799236".equals(order.getPhone())) {
        // 发送mq异步处理
        RebateMq rebateMq = new RebateMq();
        rebateMq.setUserId(order.getUserId());
        rebateMq.setOrderId(order.getId());
        String msg = JsonUtils.toJsonString(rebateMq);
        rabbitMQService.sendFanoutMessage(FanoutRabbitMQConfig.REBATE_ROUTING_KEY, msg);
        // 发送mq异步处理 退款
        rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.REFUND_ROUTING_KEY, order.getId().toString(), 2*60 *1000);
        throw new ServiceException("测试退款手机号");
      }
      // 将 List 转换为 JSON 字符串
      String showInforStr = JSONUtil.toJsonStr(showInfor);
      // 准备请求参数
      Map<String, String> params = new HashMap<>(12);
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
      if(JfshouOrderSubmitResponseCodeEnum.isErrorCode(code)) {
        // 发送mq异步处理 退款
        rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.REFUND_ROUTING_KEY, order.getId().toString(), 2*60 *1000);
        throw new ServiceException(message);
      } else {
        // 发送mq异步处理 同步出票信息
        SyncCodeMq syncCodeMq = new SyncCodeMq();
        syncCodeMq.setOrderId(order.getId());
        syncCodeMq.setNum(1);
        String msg = JsonUtils.toJsonString(syncCodeMq);
        rabbitMQService.sendDelayedMessage(DelayRabbitMQConfig.SYNC_CODE_ROUTING_KEY, msg, 3*60 *1000);
      }
    } catch (Exception e) {
      log.error("调用三方下单异常", e);
    }
  }

}
