package com.vscing.api.service.impl;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vscing.api.service.NotifyService;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.AppletServiceFactory;
import com.vscing.common.service.supplier.SupplierService;
import com.vscing.common.service.supplier.SupplierServiceFactory;
import com.vscing.common.utils.JsonUtils;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.model.dto.SeatListDto;
import com.vscing.model.dto.ShowInforDto;
import com.vscing.model.entity.Order;
import com.vscing.model.entity.Show;
import com.vscing.model.mapper.OrderDetailMapper;
import com.vscing.model.mapper.OrderMapper;
import com.vscing.model.mapper.ShowMapper;
import com.vscing.mq.config.RabbitMQConfig;
import com.vscing.mq.service.RabbitMQService;
import com.wechat.pay.java.service.payments.model.Transaction;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * NotifyServiceImpl
 *
 * @author vscing
 * @date 2025/1/21 23:03
 */
@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {

  private static final List<Integer> SUCCESS_CODES = Arrays.asList(200, -530, 9999, 9008, 9011);

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
      log.info("提交异步出票任务，订单号: {}", orderSn);
      ticketOrder(orderSn);
      // 返回结果
      return true;
    } catch (Exception e) {
      log.error("支付宝回调异常: {}", e);
      return false;
    }
  }

  @Override
  public boolean queryWechatOrder(HttpServletRequest request) {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      try (BufferedReader reader = request.getReader()) {
        String line;
        while ((line = reader.readLine()) != null) {
          stringBuilder.append(line);
        }
      }
      // 原始请求体内容
      String requestBody = stringBuilder.toString();
      log.error("微信下单异步通知请求参数: {}", requestBody);
      // 解析响应字符串为 JSON 对象
      ObjectMapper objectMapper = JsonUtils.getObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(requestBody);
      String id = jsonNode.path("id").asText(null);
      String createTime = jsonNode.path("create_time").asText(null);
      String eventType = jsonNode.path("event_type").asText(null);
      String resourceType = jsonNode.path("resource_type").asText(null);
      String summary = jsonNode.path("summary").asText(null);
      JsonNode resourceJsonNode = jsonNode.path("resource");
      String algorithm = resourceJsonNode.path("algorithm").asText(null);
      String ciphertext = resourceJsonNode.path("ciphertext").asText(null);
      String associatedData = resourceJsonNode.path("associated_data").asText(null);
      String originalType = resourceJsonNode.path("original_type").asText(null);
      String nonce = resourceJsonNode.path("nonce").asText(null);

      // 创建新的JsonNode用于存储重组后的数据
      ObjectNode newNode = objectMapper.createObjectNode();
      newNode.put("id", id);
      newNode.put("create_time", createTime);
      newNode.put("resource_type", resourceType);
      newNode.put("event_type", eventType);
      newNode.put("summary", summary);

      // 创建资源节点
      ObjectNode resourceNode = objectMapper.createObjectNode();
      resourceNode.put("original_type", originalType);
      resourceNode.put("algorithm", algorithm);
      resourceNode.put("ciphertext", ciphertext);
      resourceNode.put("associated_data", associatedData);
      resourceNode.put("nonce", nonce);
      // 将资源节点添加到新节点中
      newNode.set("resource", resourceNode);
      requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newNode);
      log.info("微信下单异步通知请求参数[转换后]：{}", requestBody);
      // 返回结果
      Map<String, String> params = new HashMap<String,String>(5);
      params.put("Wechatpay-Serial", request.getHeader("Wechatpay-Serial"));
      params.put("Wechatpay-Nonce", request.getHeader("Wechatpay-Nonce"));
      params.put("Wechatpay-Signature", request.getHeader("Wechatpay-Signature"));
      params.put("Wechatpay-Timestamp", request.getHeader("Wechatpay-Timestamp"));
      params.put("requestBody", requestBody);
      // 支付类
      AppletService appletService = appletServiceFactory.getAppletService("wechat");
      // 签名认证
      Transaction transaction = (Transaction) appletService.signValidation(params);
      if (!Objects.nonNull(transaction)) {
        throw new ServiceException("微信订单验证签名未通过");
      }
      // 查询微信订单
      Map<String, String> queryData = new HashMap<>(10);
      queryData.put("out_trade_no", transaction.getOutTradeNo());
      queryData.put("transaction_id", transaction.getTransactionId());
      boolean res = appletService.queryOrder(params);
      if (!res) {
        throw new ServiceException("微信订单支付未成功");
      }
      String orderSn = params.get("out_trade_no");
      String tradeNo = params.get("transaction_id");
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
      log.error("微信回调异常: {}", e);
      return false;
    }
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
      // 发送mq异步处理
      rabbitMQService.sendDelayedMessage(RabbitMQConfig.SYNC_CODE_ROUTING_KEY, order.getId().toString(), 2*60 *1000);
      // 调用三方成功
      if(SUCCESS_CODES.contains(code)) {
        log.error("调用三方下单写入数据：", order.getOrderSn());
      } else {
        throw new ServiceException(message);
      }
    } catch (Exception e) {
      log.error("调用三方下单异常：{}", e);
    }
  }

}
