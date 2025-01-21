package com.vscing.common.service.applet.impl.alipay;

import cn.hutool.http.HttpException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.diagnosis.DiagnosisUtils;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipayEncrypt;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.common.service.OkHttpService;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * AppletServiceImpl
 *
 * @author vscing
 * @date 2025/1/7 19:25
 */
@Slf4j
@Service("alipayAppletService")
public class AppletServiceImpl implements AppletService {

  private static final String ALIPAY_BASH_URL = "https://openapi.alipay.com/gateway.do";

  @Autowired
  private OkHttpService okHttpService;

  @Autowired
  private AppletProperties appletProperties;

  /**
   * 配置文件
   */
  private AlipayConfig getAlipayConfig() {
    AlipayConfig alipayConfig = new AlipayConfig();
    //设置连接池中的最大可缓存的空闲连接数
    alipayConfig.setMaxIdleConnections(5);
    //连接超时，单位：毫秒，默认3000
    alipayConfig.setConnectTimeout(3000);
    //读取超时，单位：毫秒，默认15000
    alipayConfig.setReadTimeout(15000);
    //空闲连接存活时间，单位：毫秒，默认10000L
    alipayConfig.setKeepAliveDuration(10000L);
    //设置网关地址
    alipayConfig.setServerUrl(ALIPAY_BASH_URL);
    //设置请求格式，固定值json
    alipayConfig.setFormat("JSON");
    //设置字符集
    alipayConfig.setCharset("UTF-8");
    //设置签名类型
    alipayConfig.setSignType("RSA2");
    //设置应用ID
    alipayConfig.setAppId(appletProperties.getAppId());
    //设置应用私钥
    alipayConfig.setPrivateKey(appletProperties.getPrivateKey());
    //设置应用公钥证书路径
    alipayConfig.setAppCertPath(appletProperties.getAppCertPath());
    //设置支付宝公钥证书路径
    alipayConfig.setAlipayPublicCertPath(appletProperties.getAlipayPublicCertPath());
    //设置支付宝根证书路径
    alipayConfig.setRootCertPath(appletProperties.getRootCertPath());
    // 返回配置
    return alipayConfig;
  }

  /**
   * 验证签名
   */
  private boolean rsaCheck(String signContent, String sign, String signVeriKey, String charset, String signType) throws AlipayApiException {
    try {
      return AlipaySignature.rsaCheck(signContent, sign, signVeriKey, charset, signType);
    } catch (AlipayApiException e) {
      log.error("验签异常", e);
      return false;
    }
  }

  @Override
  public String getPhoneNumber(String code) {
    try {
      // 解析并初始化参数
      Map<String, String> openapiResult = JSON.parseObject(code, new TypeReference<Map<String, String>>() {}, Feature.OrderedField);
      String signType = "RSA2";
      String charset = "UTF-8";
      String encryptType = "AES";
      String sign = openapiResult.get("sign");
      String content = openapiResult.get("response");

      // 判断是否为加密内容
      boolean isDataEncrypted = !content.startsWith("{");
      String signContent = isDataEncrypted ? "\"" + content + "\"" : content;
      String signVeriKey = AlipaySignature.getAlipayPublicKey(appletProperties.getAlipayPublicCertPath());
      String decryptKey = appletProperties.getAes();

      // 验签
      if (!rsaCheck(signContent, sign, signVeriKey, charset, signType)) {
        throw new SecurityException("验签失败");
      }

      // 解密
      String plainData = isDataEncrypted
          ? AlipayEncrypt.decryptContent(content, encryptType, decryptKey, charset)
          : content;

      // 解析响应字符串为 JSON 对象
      ObjectMapper objectMapper = JsonUtils.getObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(plainData);

      // 检查是否有错误信息
      if (jsonNode.has("code") && !"10000".equals(jsonNode.get("code").asText())) {
        throw new HttpException("支付宝获取手机号失败: " + jsonNode.toPrettyString());
      }

      // 获取 mobile
      String mobile = jsonNode.path("mobile").asText(null);
      if (mobile != null && !mobile.isEmpty()) {
        return mobile;
      } else {
        throw new RuntimeException("支付宝获取手机号未找到有效的 mobile");
      }
    } catch (AlipayApiException e) {
      log.error("支付宝 API 调用异常", e);
      throw new HttpException("支付宝获取手机号方法异常: " + e.getMessage(), e);
    } catch (SecurityException e) {
      log.error("安全验证异常", e);
      throw new HttpException("支付宝获取手机号方法异常: " + e.getMessage(), e);
    } catch (Exception e) {
      log.error("支付宝获取手机号方法异常", e);
      throw new HttpException("支付宝获取手机号方法异常: " + e.getMessage(), e);
    }
  }

  @Override
  public JsonNode getOpenid(String code) {
    try {
      // 初始化SDK
      AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
      // 构造请求参数以调用接口
      AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
      // 设置授权码
      request.setCode(code);
      // 设置授权方式
      request.setGrantType("authorization_code");
      // 发起请求
      AlipaySystemOauthTokenResponse response = alipayClient.certificateExecute(request);
      log.info("支付宝获取openid调用结果: " , response);
      if (response.isSuccess()) {
        // 将响应字符串解析为 JSON 对象
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        return jsonNode;
      } else {
        // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
        String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
        throw new HttpException("支付宝获取openid失败: " + diagnosisUrl);
      }
    } catch (Exception e) {
      log.error("支付宝获取openid方法异常", e);
      throw new HttpException("支付宝获取openid方法异常: " + e.getMessage(), e);
    }
  }

  @Override
  public String getPayment(Map<String, Object> paymentData) {
    try {
      // 初始化SDK
      AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
      // 构造请求参数以调用接口
      AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
      // 设置异步通知接口
      request.setNotifyUrl("https://api.hiyaflix.cn/v1/notify/alipayCreate");
      // 业务请求参数
      AlipayTradeCreateModel model = new AlipayTradeCreateModel();
      // 设置商户订单号
      model.setOutTradeNo((String) paymentData.get("outTradeNo"));
      // 设置订单总金额
//      model.setTotalAmount((String) paymentData.get("totalAmount"));
      model.setTotalAmount("0.01");
      // 设置订单标题
      model.setSubject("嗨呀电影票订单" + paymentData.get("outTradeNo"));
      // 设置产品码
      model.setProductCode("JSAPI_PAY");
      // 设置小程序支付中
      model.setOpAppId(appletProperties.getAppId());
      // 设置买家支付宝用户唯一标识
      model.setBuyerOpenId((String) paymentData.get("openid"));
      // 设置订单相对超时时间
      model.setTimeoutExpress("10m");
      // 请求参数的集合
      request.setBizModel(model);
      // 调用接口
      AlipayTradeCreateResponse response = alipayClient.certificateExecute(request);
      log.info("支付宝下单调用结果: " , response);
      if (response.isSuccess()) {
        // 将响应字符串解析为 JSON 对象
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        jsonNode = jsonNode.path("alipay_trade_create_response");
        if (!jsonNode.isMissingNode()) {
          // 获取 tradeNo
          String tradeNo = jsonNode.path("trade_no").asText(null);
          if (tradeNo != null && !tradeNo.isEmpty()) {
            return tradeNo;
          } else {
            throw new RuntimeException("支付宝下单未获取到有效的 trade_no");
          }
        } else {
          throw new RuntimeException("支付宝下单未获取到有效的 alipay_trade_create_response");
        }
      } else {
        // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
        String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
        throw new HttpException("支付宝下单接口失败: " + diagnosisUrl);
      }
    } catch (Exception e) {
      log.error("支付宝下单方法异常", e);
      throw new HttpException("支付宝下单方法异常: " + e.getMessage(), e);
    }
  }

  @Override
  public boolean queryOrder(Map<String, String> queryData) {
    try {
      // 初始化SDK
      AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
      // 构造请求参数以调用接口
      AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
      AlipayTradeQueryModel model = new AlipayTradeQueryModel();
      // 设置订单支付时传入的商户订单号
      model.setOutTradeNo(queryData.get("outTradeNo"));
      // 设置支付宝交易号
      model.setTradeNo(queryData.get("tradeNo"));
      // 请求参数的集合
      request.setBizModel(model);
      // 调用接口
      AlipayTradeQueryResponse response = alipayClient.execute(request);
      log.info("支付宝查询订单调用结果: ", response);
      if (response.isSuccess()) {
        // 将响应字符串解析为 JSON 对象
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        jsonNode = jsonNode.path("alipay_trade_create_response");
        if (!jsonNode.isMissingNode()) {
          // 获取 tradeStatus
          String tradeStatus = jsonNode.path("trade_status").asText(null);
          if (tradeStatus != null && !tradeStatus.isEmpty()) {
            return "TRADE_SUCCESS".equals(tradeStatus);
          } else {
            throw new RuntimeException("支付宝下单未获取到有效的 trade_no");
          }
        } else {
          throw new RuntimeException("支付宝下单未获取到有效的 alipay_trade_create_response");
        }
      } else {
        // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
        String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
        throw new HttpException("支付宝查询订单接口失败: " + diagnosisUrl);
      }
    } catch (Exception e) {
      log.error("支付宝查询订单方法异常", e);
      throw new HttpException("支付宝查询订单方法异常: " + e.getMessage(), e);
    }
  }

}
