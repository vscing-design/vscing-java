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
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.AlipayMerchantOrderSyncModel;
import com.alipay.api.domain.AlipayOpenAppQrcodeCreateModel;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.ItemOrderInfo;
import com.alipay.api.domain.OrderExtInfo;
import com.alipay.api.domain.Participant;
import com.alipay.api.internal.util.AlipayEncrypt;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayMerchantOrderSyncRequest;
import com.alipay.api.request.AlipayOpenAppQrcodeCreateRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayMerchantOrderSyncResponse;
import com.alipay.api.response.AlipayOpenAppQrcodeCreateResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.common.service.OkHttpService;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
      log.error("验签异常: {}", e.getMessage());
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
      if (response.isSuccess()) {
        // 将响应字符串解析为 JSON 对象
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        return objectMapper.readTree(response.getBody());
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
  public Map<String, String> getPayment(Map<String, Object> paymentData) {
    Map<String, String> res = new HashMap<>();
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
      BigDecimal totalAmount = (BigDecimal) paymentData.get("totalAmount");
      model.setTotalAmount(String.valueOf(totalAmount));
//      model.setTotalAmount("0.01");
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
      if (response.isSuccess()) {
        // 将响应字符串解析为 JSON 对象
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        jsonNode = jsonNode.path("alipay_trade_create_response");
        if (!jsonNode.isMissingNode()) {
          // 获取 tradeNo
          String tradeNo = jsonNode.path("trade_no").asText(null);
          if (tradeNo != null && !tradeNo.isEmpty()) {
            res.put("tradeNo", tradeNo);
            return res;
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
  public Object signValidation(Map<String, String> params) {
    try {
      return AlipaySignature.rsaCertCheckV1(params, appletProperties.getAlipayPublicCertPath(), "UTF-8", "RSA2");
    } catch (Exception e) {
      log.error("支付宝签名验证失败: {}", e.getMessage());
    }
    return false;
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
      model.setOutTradeNo(queryData.get("out_trade_no"));
      // 设置支付宝交易号
      model.setTradeNo(queryData.get("trade_no"));
      // 请求参数的集合
      request.setBizModel(model);
      // 调用接口
      AlipayTradeQueryResponse response = alipayClient.certificateExecute(request);
      log.error("支付宝查询订单调用结果: {}", response.getBody());
      if (response.isSuccess()) {
        // 将响应字符串解析为 JSON 对象
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        jsonNode = jsonNode.path("alipay_trade_query_response");
        if (!jsonNode.isMissingNode()) {
          // 获取 tradeStatus
          String tradeStatus = jsonNode.path("trade_status").asText(null);
          if (tradeStatus != null && !tradeStatus.isEmpty()) {
            return "TRADE_SUCCESS".equals(tradeStatus);
          } else {
            throw new RuntimeException("支付宝查询订单未获取到有效的 trade_no");
          }
        } else {
          throw new RuntimeException("支付宝查询订单未获取到有效的 alipay_trade_create_response");
        }
      } else {
        // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
        String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
        throw new HttpException("支付宝查询订单接口失败: " + diagnosisUrl);
      }
    } catch (Exception e) {
      log.error("支付宝查询订单方法异常: {}", e.getMessage());
      throw new HttpException("支付宝查询订单方法异常: " + e.getMessage(), e);
    }
  }

  @Override
  public boolean refundOrder(Map<String, Object> refundData) {
    try {
      // 初始化SDK
      AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
      // 构造请求参数以调用接口
      AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
      // 业务请求参数
      AlipayTradeRefundModel model = new AlipayTradeRefundModel();
      // 设置商户订单号
      model.setOutTradeNo((String) refundData.get("outTradeNo"));
      // 设置支付宝交易号
      model.setTradeNo((String) refundData.get("tradeNo"));
      // 设置退款请求号
      model.setOutRequestNo((String) refundData.get("refundNo"));
      // 设置退款金额
      BigDecimal totalAmount = (BigDecimal) refundData.get("totalAmount");
      model.setRefundAmount(String.valueOf(totalAmount));
      // 请求参数的集合
      request.setBizModel(model);
      // 调用接口
      AlipayTradeRefundResponse response = alipayClient.certificateExecute(request);
      log.info("支付宝退款调用结果: {}" , response.getBody());

      if (response.isSuccess()) {
        // 将响应字符串解析为 JSON 对象
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        jsonNode = jsonNode.path("alipay_trade_refund_response");
        if (!jsonNode.isMissingNode()) {
          // 获取 fundChange
          String fundChange = jsonNode.path("fund_change").asText(null);
          if (fundChange != null && !fundChange.isEmpty()) {
            return "Y".equals(fundChange);
          } else {
            throw new RuntimeException("支付宝退款未获取到有效的 fund_change");
          }
        } else {
          throw new RuntimeException("支付宝退款未获取到有效的 alipay_trade_refund_response");
        }
      } else {
        // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
        String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
        throw new HttpException("支付宝退款接口失败: " + diagnosisUrl);
      }
    } catch (Exception e) {
      log.error("支付宝退款方法异常: {}", e.getMessage());
      throw new HttpException("支付宝退款方法异常: " + e.getMessage(), e);
    }
  }

  @Override
  public boolean queryRefund(Map<String, String> queryData) {
    try {
      // 初始化SDK
      AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
      // 构造请求参数以调用接口
      AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
      // 业务请求参数
      AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
      // 设置商户订单号
      model.setOutTradeNo(queryData.get("outTradeNo"));
      // 设置支付宝交易号
      model.setTradeNo(queryData.get("tradeNo"));
      // 设置退款请求号
      model.setOutRequestNo(queryData.get("refundNo"));
      // 请求参数的集合
      request.setBizModel(model);
      // 调用接口
      AlipayTradeFastpayRefundQueryResponse response = alipayClient.certificateExecute(request);
      log.info("支付宝查询退款订单调用结果: {}" , response.getBody());

      if (response.isSuccess()) {
        // 将响应字符串解析为 JSON 对象
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        jsonNode = jsonNode.path("alipay_trade_fastpay_refund_query_response");
        if (!jsonNode.isMissingNode()) {
          // 获取 refund_status
          String refundStatus = jsonNode.path("refund_status").asText(null);
          if (refundStatus != null && !refundStatus.isEmpty()) {
            return "REFUND_SUCCESS".equals(refundStatus);
          } else {
            throw new RuntimeException("支付宝查询退款订单未获取到有效的 refund_status");
          }
        } else {
          throw new RuntimeException("支付宝查询退款订单未获取到有效的 alipay_trade_fastpay_refund_query_response");
        }
      } else {
        // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
        String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
        throw new HttpException("支付宝查询退款订单接口失败: " + diagnosisUrl);
      }
    } catch (Exception e) {
      log.error("支付宝查询退款订单方法异常: {}", e.getMessage());
      throw new HttpException("支付宝查询退款订单方法异常: " + e.getMessage(), e);
    }
  }

  @Override
  public String getQrcode(Map<String, Object> queryData) {
    try {
      // 初始化SDK
      AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
      // 构造请求参数以调用接口
      AlipayOpenAppQrcodeCreateRequest request = new AlipayOpenAppQrcodeCreateRequest();
      AlipayOpenAppQrcodeCreateModel model = new AlipayOpenAppQrcodeCreateModel();
      // 设置跳转小程序的页面路径
      model.setUrlParam(queryData.get("url").toString());
      // 设置小程序的启动参数
      model.setQueryParam(queryData.get("query").toString());
      // 设置码描述
      model.setDescribe("邀请码");
      request.setBizModel(model);
      // 调用接口
      AlipayOpenAppQrcodeCreateResponse response = alipayClient.certificateExecute(request);
      log.info("支付宝获取小程序二维码调用结果: {}" , response.getBody());
      if (response.isSuccess()) {
        // 将响应字符串解析为 JSON 对象
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        jsonNode = jsonNode.path("alipay_open_app_qrcode_create_response");
        if (!jsonNode.isMissingNode()) {
          // 获取 小程序码
          String qrcode = jsonNode.path("qr_code_url").asText(null);
          if (qrcode != null && !qrcode.isEmpty()) {
            return qrcode;
          } else {
            throw new RuntimeException("支付宝获取小程序二维码未获取到有效的 qr_code_url");
          }
        } else {
          throw new RuntimeException("支付宝获取小程序二维码未获取到有效的 alipay_open_app_qrcode_create_response");
        }
      } else {
        // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
        String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
        throw new HttpException("支付宝获取小程序二维码接口失败: " + diagnosisUrl);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Object transferOrder(Map<String, Object> transferData) {
    // 接口文档： https://opendocs.alipay.com/open/62987723_alipay.fund.trans.uni.transfer?scene=ca56bca529e64125a2786703c6192d41&pathHash=66064890
    try {
      // 初始化SDK
      AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
      // 构造请求参数以调用接口
      AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
      AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
      // 设置商家侧唯一订单号
      model.setOutBizNo(transferData.get("withdrawSn").toString());
      // 设置订单总金额
      BigDecimal amount = (BigDecimal) transferData.get("amount");
      model.setTransAmount(amount.toString());
      // 设置描述特定的业务场景
      model.setBizScene("DIRECT_TRANSFER");
      // 设置业务产品码
      model.setProductCode("TRANS_ACCOUNT_NO_PWD");
      // 设置转账业务的标题
      model.setOrderTitle("嗨丫提现转账");
      // 设置收款方信息
      Participant payeeInfo = new Participant();
      payeeInfo.setIdentityType("ALIPAY_OPEN_ID");
      payeeInfo.setIdentity(transferData.get("openid").toString());
//      payeeInfo.setCertType("IDENTITY_CARD");
//      payeeInfo.setCertNo("1201152******72917");
//      payeeInfo.setName("黄龙国际有限公司");
      model.setPayeeInfo(payeeInfo);
      // 设置业务备注
      model.setRemark("嗨丫提现转账");
      // 设置转账业务请求的扩展参数
//      model.setBusinessParams("{\"payer_show_name_use_alias\":\"true\"}");
      request.setBizModel(model);
      AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);
      if (response.isSuccess()) {
        log.info("支付宝转账成功: {}", response.getBody());
        return response;
      } else {
        String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
        throw new HttpException("支付宝转账失败: " + diagnosisUrl);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void syncOrder(Map<String, Object> syncOrderData) {
    try {
      // 初始化SDK
      AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
      // 构造请求参数以调用接口
      AlipayMerchantOrderSyncRequest request = new AlipayMerchantOrderSyncRequest();
      AlipayMerchantOrderSyncModel model = new AlipayMerchantOrderSyncModel();
      // 设置外部订单号
      model.setOutBizNo((String) syncOrderData.get("outBizNo"));
      // 设置订单修改时间
      LocalDateTime orderModifiedTime = (LocalDateTime) syncOrderData.get("orderModifiedTime");
      model.setOrderModifiedTime(Date.from(orderModifiedTime.atZone(ZoneId.systemDefault()).toInstant()));
      // buyer_open_id
      model.setBuyerOpenId((String) syncOrderData.get("buyerOpenId"));
      // source_app
      model.setSourceApp("Alipay");
      // 订单类型
      model.setOrderType("SERVICE_ORDER");
      // 订单金额
      BigDecimal amount = (BigDecimal) syncOrderData.get("amount");
      model.setAmount(String.valueOf(amount));
      // 优惠金额
      model.setDiscountAmount("0");
      // 支付金额
      model.setPayAmount(String.valueOf(amount));
      // 订单创建时间
      LocalDateTime orderCreateTime = (LocalDateTime) syncOrderData.get("orderCreateTime");
      model.setOrderCreateTime(Date.from(orderCreateTime.atZone(ZoneId.systemDefault()).toInstant()));
      // 设置商品信息列表
      List<ItemOrderInfo> itemOrderList = new ArrayList<ItemOrderInfo>();
      // 单个商品信息
      ItemOrderInfo itemOrderInfo = new ItemOrderInfo();
      // 商品名称
      itemOrderInfo.setItemName("电影票");
      // 商品数量
      int purchaseQuantity = (int) syncOrderData.get("purchaseQuantity");
      itemOrderInfo.setQuantity((long) purchaseQuantity);
      // 单价
      BigDecimal price = amount.divide(BigDecimal.valueOf(purchaseQuantity), 2, RoundingMode.HALF_UP);
      itemOrderInfo.setUnitPrice(String.valueOf(price));
      // 单位
      itemOrderInfo.setUnit("张");
      // 添加商品扩展信息
//      List<OrderExtInfo> extInfoJNbyF = new ArrayList<OrderExtInfo>();
//      OrderExtInfo extInfoJNbyF0 = new OrderExtInfo();
//      extInfoJNbyF0.setExtKey("MY_KEY");
//      extInfoJNbyF0.setExtValue("MY_VALUE");
//      extInfoJNbyF.add(extInfoJNbyF0);
//      itemOrderInfo.setExtInfo(extInfoJNbyF);
      itemOrderList.add(itemOrderInfo);
      model.setItemOrderList(itemOrderList);
      // 设置其它扩展信息
      List<OrderExtInfo> extInfoList = new ArrayList<OrderExtInfo>();
      OrderExtInfo extInfoTHceR0 = new OrderExtInfo();
      extInfoTHceR0.setExtKey("merchant_biz_type");
      extInfoTHceR0.setExtValue("MOVIE_TICKET");
      extInfoList.add(extInfoTHceR0);
      OrderExtInfo extInfoTHceR1 = new OrderExtInfo();
      extInfoTHceR1.setExtKey("merchant_order_status");
      extInfoTHceR1.setExtValue("FINISHED");
      extInfoList.add(extInfoTHceR1);
      OrderExtInfo extInfoTHceR2 = new OrderExtInfo();
      extInfoTHceR2.setExtKey("merchant_order_link_page");
      extInfoTHceR2.setExtValue("/pages/home/index/index");
      extInfoList.add(extInfoTHceR2);
      OrderExtInfo extInfoTHceR3 = new OrderExtInfo();
      extInfoTHceR3.setExtKey("tiny_app_id");
      extInfoTHceR3.setExtValue(appletProperties.getAppId());
      extInfoList.add(extInfoTHceR3);
      model.setExtInfo(extInfoList);
      request.setBizModel(model);
      // 请求接口
      AlipayMerchantOrderSyncResponse response = alipayClient.certificateExecute(request);
      log.info("支付宝同步订单：{}", response.getBody());
      if (response.isSuccess()) {
        log.info("支付宝同步订单成功");
      } else {
        String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
        throw new HttpException("支付宝同步订单失败: " + diagnosisUrl);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
