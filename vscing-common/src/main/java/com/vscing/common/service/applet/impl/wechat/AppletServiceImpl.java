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
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.payments.jsapi.model.QueryOrderByIdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    // 返回配置
    return new RSAAutoCertificateConfig.Builder()
        .merchantId(appletProperties.getMerchantId())
        .privateKeyFromPath(appletProperties.getPrivateKeyPath())
        .merchantSerialNumber(appletProperties.getMerchantSerialNumber())
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
      Integer expiresIn = jsonNode.path("expires_in").asInt();

      if (StringUtils.isNotBlank(accessToken) && expiresIn > 0) {
        // 设置到 Redis 并返回 access_token
        redisService.set(getKey(), accessToken, expiresIn);
        log.info("getAccessToken 调用成功");
        return accessToken;
      } else {
        throw new HttpException("getAccessToken access_token 或 expires_in 值错误: " + jsonNode.toPrettyString());
      }
    } catch (IOException e) {
      throw new HttpException("getAccessToken 微信API调用异常: " + e.getMessage(), e);
    } catch (Exception e) {
      log.error("getAccessToken 方法异常", e);
      throw new HttpException("getAccessToken 方法异常: " + e.getMessage(), e);
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
  public String getPayment(Map<String, Object> paymentData) {
    try {
      JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(getConfig()).build();

      // 跟之前下单示例一样，填充预下单参数
      PrepayRequest request = new PrepayRequest();

      Amount amount = new Amount();
      amount.setTotal(100);
      request.setAmount(amount);
      request.setAppid("wxa9d9651ae******");
      request.setMchid("190000****");
      request.setDescription("测试商品标题");
      request.setNotifyUrl("https://notify_url");
      request.setOutTradeNo("out_trade_no_001");

      // response包含了调起支付所需的所有参数，可直接用于前端调起支付
      PrepayWithRequestPaymentResponse response = service.prepayWithRequestPayment(request);
    } catch (Exception e) {

    }
    return "";
  }

  @Override
  public boolean queryOrder(Map<String, String> queryData) {
//    https://github.com/wechatpay-apiv3/wechatpay-java
    JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(getConfig()).build();

    QueryOrderByIdRequest queryRequest = new QueryOrderByIdRequest();
    queryRequest.setMchid("190000****");
    queryRequest.setTransactionId("4200001569202208304701234567");

    try {
      Transaction result = service.queryOrderById(queryRequest);
      System.out.println(result.getTradeState());
    } catch (ServiceException e) {
      // API返回失败, 例如ORDER_NOT_EXISTS
      System.out.printf("code=[%s], message=[%s]\n", e.getErrorCode(), e.getErrorMessage());
      System.out.printf("reponse body=[%s]\n", e.getResponseBody());
    }
    return false;
  }

}
