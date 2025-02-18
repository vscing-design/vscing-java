package com.vscing.common.service.applet.impl.wechat;

import cn.hutool.http.HttpException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.common.service.OkHttpService;
import com.vscing.common.service.RedisService;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.utils.JsonUtils;
import com.vscing.common.utils.StringUtils;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAPublicKeyConfig;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RSAPublicKeyNotificationConfig;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.payments.jsapi.model.QueryOrderByIdRequest;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.AmountReq;
import com.wechat.pay.java.service.refund.model.CreateRequest;
import com.wechat.pay.java.service.refund.model.QueryByOutRefundNoRequest;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * AppletServiceImpl
 *
 * @author vscing
 * @date 2025/1/7 19:25
 */
@Slf4j
@Service("wechatAppletService")
public class AppletServiceImpl implements AppletService {

  private static final String CACHE_KEY_PREFIX = "vscing—wechat";
  private static final String WECHAT_BASH_URL = "https://api.weixin.qq.com";

  private String key;

  @Autowired
  private AppletProperties appletProperties;

  @Autowired
  private RedisService redisService;

  @Autowired
  private OkHttpService okHttpService;

  /**
   * 配置文件
   */
  private Config getConfig() {
    // 可以根据实际情况使用publicKeyFromPath或publicKey加载公钥
    return new RSAPublicKeyConfig.Builder()
        //微信支付的商户号
        .merchantId(appletProperties.getMerchantId())
        // 商户API证书私钥的存放路径
        .privateKeyFromPath(appletProperties.getPrivateKeyPath())
        // 微信支付公钥的存放路径
        .publicKeyFromPath(appletProperties.getPublicKeyPath())
        //微信支付公钥ID
        .publicKeyId(appletProperties.getPublicKeyId())
        //商户API证书序列号
        .merchantSerialNumber(appletProperties.getMerchantSerialNumber())
        //APIv3密钥
        .apiV3Key(appletProperties.getApiV3Key())
        .build();
  }

  /**
   * 配置文件
   */
  private NotificationConfig getNotificationConfig() {
    return new RSAPublicKeyNotificationConfig.Builder()
        // 微信支付公钥的存放路径
        .publicKeyFromPath(appletProperties.getPublicKeyPath())
        //微信支付公钥ID
        .publicKeyId(appletProperties.getPublicKeyId())
        //APIv3密钥
        .apiV3Key(appletProperties.getApiV3Key())
        .build();
  }

  /**
   * 缓存key
   */
  private String getKey() {
    if (this.key == null) {
      this.key = String.format("%s.access_token.%s.%s.%d",
          CACHE_KEY_PREFIX, appletProperties.getAppId(), appletProperties.getAppSecret(), appletProperties.getStable() ? 1 : 0);
    }
    return this.key;
  }

  /**
   * 获取token
   */
  private String getToken() {
    String token = (String) redisService.get(this.getKey());

    // 检查 token 是否为 null 或空字符串
    if (StringUtils.isNotBlank(token)) {
      return token;
    }

    return this.refresh();
  }

  /**
   * 刷新token
   */
  private String refresh() {
    return appletProperties.getStable() ? this.getStableAccessToken(false) : this.getAccessToken();
  }

  /**
   * 调用 getStableAccessToken
   */
  private String getStableAccessToken(boolean forceRefresh) {
    try {
      Map<String, Object> params = Map.of(
          "grant_type", "client_credential",
          "appid", appletProperties.getAppId(),
          "secret", appletProperties.getAppSecret(),
          "force_refresh", forceRefresh
      );
      // 发送 POST 请求并获取响应
      String response = okHttpService.doPostJson(WECHAT_BASH_URL + "/cgi-bin/stable_token", JsonUtils.toJsonString(params), null);
      // 将响应字符串解析为 JSON 对象
      ObjectMapper objectMapper = JsonUtils.getObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(response);
      // 检查是否有错误信息
      if (!jsonNode.has("access_token")) {
        throw new HttpException("getStableAccessToken 缺少access_token: " + jsonNode.toPrettyString());
      }
      // 提取 access_token 和过期时间
      String accessToken = jsonNode.path("access_token").asText();
      Integer expiresIn = jsonNode.path("expires_in").asInt();

      if (StringUtils.isNotBlank(accessToken) && expiresIn > 0) {
        // 设置到 Redis 并返回 access_token
        redisService.set(getKey(), accessToken, expiresIn);
        log.info("getStableAccessToken 调用成功");
        return accessToken;
      } else {
        throw new HttpException("getStableAccessToken access_token 或 expires_in 值错误: " + jsonNode.toPrettyString());
      }
    } catch (IOException e) {
      throw new HttpException("getStableAccessToken 微信API调用异常: " + e.getMessage(), e);
    } catch (Exception e) {
      log.error("getStableAccessToken 方法异常", e);
      throw new HttpException("getStableAccessToken 方法异常: " + e.getMessage(), e);
    }
  }

  /**
   * 调用 getAccessToken
   */
  private String getAccessToken() {
    try {
      Map<String, String> params = Map.of(
          "grant_type", "client_credential",
          "appid", appletProperties.getAppId(),
          "secret", appletProperties.getAppSecret()
      );
      // 发送 POST 请求并获取响应
      String response = okHttpService.doGet(WECHAT_BASH_URL + "/cgi-bin/token", params, null);
      // 将响应字符串解析为 JSON 对象
      ObjectMapper objectMapper = JsonUtils.getObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(response);
      // 检查是否有错误信息
      if (!jsonNode.has("access_token")) {
        throw new HttpException("getAccessToken 缺少access_token: " + jsonNode.toPrettyString());
      }
      // 提取 access_token 和过期时间
      String accessToken = jsonNode.path("access_token").asText();
      long expiresIn = jsonNode.path("expires_in").asLong();
      // 存储 access_token
      if (StringUtils.isNotBlank(accessToken) && expiresIn > 0) {
        // 设置到 Redis 并返回 access_token
        redisService.set(getKey(), accessToken, expiresIn);
        return accessToken;
      }
      throw new HttpException("getAccessToken access_token 或 expires_in 值错误: " + jsonNode.toPrettyString());
    } catch (IOException e) {
      throw new HttpException("getAccessToken 微信API调用异常: " + e.getMessage(), e);
    } catch (Exception e) {
      log.error("getAccessToken 方法异常", e);
      throw new HttpException("getAccessToken 方法异常: " + e.getMessage(), e);
    }
  }

  @Override
  public JsonNode getOpenid(String code) {
    try {
      // 组装请求参数
      Map<String, String> params = Map.of(
          "appid", appletProperties.getAppId(),
          "secret", appletProperties.getAppSecret(),
          "js_code", code,
          "grant_type", "authorization_code"
      );
      // 发送 GET 请求并获取响应
      String response = okHttpService.doGet(WECHAT_BASH_URL + "/sns/jscode2session", params, null);
      log.info("微信获取openidAPI调用结果: " , response);
      // 将响应字符串解析为 JSON 对象
      ObjectMapper objectMapper = JsonUtils.getObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(response);
      // 检查是否有错误信息
      if (jsonNode.has("errcode") && jsonNode.get("errcode").asInt() != 0) {
        throw new HttpException("微信获取openid失败: " + jsonNode.toPrettyString());
      }
      return jsonNode;
    } catch (IOException e) {
      throw new HttpException("微信获取openidAPI调用异常: " + e.getMessage(), e);
    } catch (Exception e) {
      log.error("微信获取openid方法异常", e);
      throw new HttpException("微信获取openid方法异常: " + e.getMessage(), e);
    }
  }

  @Override
  public String getPhoneNumber(String code) {
    try {
      // 获取token
      String token = getToken();
      // 组装请求参数
      Map<String, Object> params = Map.of(
              "code", code
      );
      // 发送 POST 请求并获取响应
      String response = okHttpService.doPostJson(WECHAT_BASH_URL + "/wxa/business/getuserphonenumber?access_token=" + token, JsonUtils.toJsonString(params), null);
      log.info("微信获取手机号调用结果: " , response);
      // 将响应字符串解析为 JSON 对象
      ObjectMapper objectMapper = JsonUtils.getObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(response);
      // 检查是否有错误信息
      if (jsonNode.has("errcode") && jsonNode.get("errcode").asInt() != 0) {
        throw new HttpException("微信获取手机号失败: " + jsonNode.toPrettyString());
      }
      // 导航到 phone_info 节点并获取 purePhoneNumber
      JsonNode phoneInfoNode = jsonNode.path("phone_info");
      if (!phoneInfoNode.isMissingNode()) {
        String purePhoneNumber = phoneInfoNode.path("purePhoneNumber").asText(null);
        if (purePhoneNumber != null && !purePhoneNumber.isEmpty()) {
          return purePhoneNumber;
        } else {
          throw new RuntimeException("微信获取手机号未找到有效的 purePhoneNumber");
        }
      } else {
        throw new RuntimeException("微信获取手机号未找到 phone_info 节点");
      }
    } catch (IOException e) {
      throw new HttpException("微信获取手机号API调用异常: " + e.getMessage(), e);
    } catch (Exception e) {
      log.error("微信获取手机号方法异常", e);
      throw new HttpException("微信获取手机号方法异常: " + e.getMessage(), e);
    }
  }

  @Override
  public Map<String, String> getPayment(Map<String, Object> paymentData) {
    Map<String, String> res = new HashMap<>();
    try {
      JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(getConfig()).build();

      // 跟之前下单示例一样，填充预下单参数
      PrepayRequest request = new PrepayRequest();

      Amount amount = new Amount();
      BigDecimal totalAmount = (BigDecimal) paymentData.get("totalAmount");
      BigDecimal multipliedAmount = totalAmount.multiply(BigDecimal.valueOf(100));
      amount.setTotal(multipliedAmount.intValueExact());
//      amount.setTotal(1);
      request.setAmount(amount);
      request.setAppid(appletProperties.getAppId());
      request.setMchid(appletProperties.getMerchantId());
      Payer payer = new Payer();
      payer.setOpenid((String) paymentData.get("openid"));
      request.setPayer(payer);
      request.setDescription("嗨呀电影票订单" + paymentData.get("outTradeNo"));
      request.setNotifyUrl("https://api.hiyaflix.cn/v1/notify/wechatCreate");
      request.setOutTradeNo((String) paymentData.get("outTradeNo"));
      // 假设你需要生成一个支付结束时间并返回
      ZoneId zone = ZoneId.of("Asia/Shanghai");
      ZonedDateTime expireTime = ZonedDateTime.now(zone).plus(10, java.time.temporal.ChronoUnit.MINUTES);
      // 创建一个符合 RFC 3339 的格式化器，保留到毫秒级别
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
      // setTimeExpire 方法
      request.setTimeExpire(expireTime.format(formatter));
      // response包含了调起支付所需的所有参数，可直接用于前端调起支付
      PrepayWithRequestPaymentResponse response = service.prepayWithRequestPayment(request);
      res.put("timeStamp", response.getTimeStamp());
      res.put("nonceStr", response.getNonceStr());
      res.put("packageStr", response.getPackageVal());
      res.put("signType", response.getSignType());
      res.put("paySign", response.getPaySign());
      return res;
    } catch (Exception e) {
      log.error("微信下单方法异常", e);
      throw new HttpException("微信下单方法异常: " + e.getMessage(), e);
    }
  }

  @Override
  public Object signValidation(Map<String, String> params) {
    // 初始化 NotificationParser
    NotificationParser parser = new NotificationParser(getNotificationConfig());

    try {
      // 构造 RequestParam
      RequestParam requestParam = new RequestParam.Builder()
          .serialNumber(params.get("wechatPaySerial"))
          .nonce(params.get("wechatpayNonce"))
          .signature(params.get("wechatSignature"))
          .timestamp(params.get("wechatTimestamp"))
          .body(params.get("requestBody"))
          .build();
      // 以支付通知回调为例，验签、解密并转换成 Transaction
      Transaction transaction = parser.parse(requestParam, Transaction.class);
      return transaction;
    } catch (ValidationException e) {
      log.error("微信签名验证失败: {}", e.getMessage());
    }
    return false;
  }

  @Override
  public boolean queryOrder(Map<String, String> queryData) {
    try {
      JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(getConfig()).build();
      // 创建请求
      QueryOrderByIdRequest queryRequest = new QueryOrderByIdRequest();
      queryRequest.setMchid(appletProperties.getMerchantId());
      queryRequest.setTransactionId(queryData.get("transaction_id"));
      // 调用接口
      Transaction result = service.queryOrderById(queryRequest);
      // 根据返回值进行业务处理
      if(result.getTradeState() == Transaction.TradeStateEnum.SUCCESS) {
        return true;
      }
    } catch (ServiceException e) {
      log.error("微信查询订单方法异常: {}", e.getErrorMessage());
    }
    return false;
  }

  @Override
  public boolean refundOrder(Map<String, Object> refundData) {
    try {
      RefundService service = new RefundService.Builder().config(getConfig()).build();
      // 创建请求
      CreateRequest request = new CreateRequest();
      // 商户订单号
      request.setOutTradeNo((String) refundData.get("outTradeNo"));
      // 微信支付订单号
      request.setTransactionId((String) refundData.get("tradeNo"));
      // 商户退款单号
      request.setOutRefundNo((String) refundData.get("refundNo"));
      // 设置退款金额
      AmountReq amount = new AmountReq();
      BigDecimal totalAmount = (BigDecimal) refundData.get("totalAmount");
      BigDecimal multipliedAmount = totalAmount.multiply(BigDecimal.valueOf(100));
      amount.setRefund(multipliedAmount.longValueExact());
      amount.setTotal(multipliedAmount.longValueExact());
      amount.setCurrency("CNY");
      request.setAmount(amount);
      // 调用接口
      Refund refund = service.create(request);
      log.info("微信退款调用结果: {}", refund.getStatus());
      return refund.getStatus() == Status.SUCCESS;
    } catch (Exception e) {
      log.error("微信退款方法异常: {}", e.getMessage());
      throw new RuntimeException("微信退款方法异常: " + e.getMessage(), e);
    }
  }

  @Override
  public boolean queryRefund(Map<String, String> queryData) {
    try {
      RefundService service = new RefundService.Builder().config(getConfig()).build();
      // 创建请求
      QueryByOutRefundNoRequest request = new QueryByOutRefundNoRequest();
      // 商户退款单号
      request.setOutRefundNo(queryData.get("refundNo"));
      // 调用接口
      Refund refund = service.queryByOutRefundNo(request);
      log.info("微信查询退款订单调用结果: {}", refund.getStatus());
      return refund.getStatus() == Status.SUCCESS;
    } catch (Exception e) {
      log.error("微信查询退款订单方法异常: {}", e.getMessage());
      throw new RuntimeException("微信查询退款订单方法异常: " + e.getMessage(), e);
    }
  }

}
