package com.vscing.common.service.applet.impl.baidu;

import cn.hutool.http.HttpException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.vscing.common.service.RedisService;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.ApplyOrderRefundRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.FindByTpOrderIDRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.FindOrderRefundRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.GetAccessTokenRequest;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.GetSessionKeyV2Request;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.RSASign;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppApplyOrderRefund;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppDataDecrypt;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppFindByTpOrderID;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppFindOrderRefund;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppGetAccessToken;
import com.vscing.common.service.applet.impl.baidu.smartapp.openapi.SmartAppGetSessionKeyV2;
import com.vscing.common.utils.JsonUtils;
import com.vscing.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * AppletServiceImpl
 *
 * @author vscing
 * @date 2025/1/7 19:25
 */
@Slf4j
@Service("baiduAppletService")
public class AppletServiceImpl implements AppletService {

    private static final String CACHE_KEY_PREFIX = "vscing—baidu";

    private String key;

    private String sessionKey;

    @Autowired
    private AppletProperties appletProperties;

    @Autowired
    private RedisService redisService;

    /**
     * 缓存key
     */
    private String getKey() {
        if (this.key == null) {
            this.key = String.format("%s.access_token.%s.%s",
                CACHE_KEY_PREFIX, appletProperties.getAppKey(), appletProperties.getAppSecret());
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

        return this.getAccessToken();
    }

    /**
     * 调用 getAccessToken
     */
    private String getAccessToken() {
        try {
            // 组装参数
            GetAccessTokenRequest param  = new GetAccessTokenRequest();
            param.setGrantType("client_credentials");
            param.setClientID(appletProperties.getAppKey());
            param.setClientSecret(appletProperties.getAppSecret());
            param.setScope("smartapp_snsapi_base");
            // 发起请求
            String response = new Gson().toJson(SmartAppGetAccessToken.getAccessToken(param));
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
        } catch (Exception e) {
            log.error("getAccessToken 方法异常", e);
            throw new HttpException("getAccessToken 方法异常: " + e.getMessage(), e);
        }
    }

    /**
     * 缓存sessionKey
     */
    private String getSessionKey() {
        if (this.sessionKey == null) {
            this.sessionKey = String.format("%s.access_token.%s.%s.%s",
                CACHE_KEY_PREFIX, appletProperties.getAppId(), appletProperties.getAppSecret(), "sessionKey");
        }
        return this.sessionKey;
    }

    @Override
    public JsonNode getOpenid(String code) {
        try {
            // 获取token
            String accessToken = getToken();
            // 参数组装
            GetSessionKeyV2Request param = new GetSessionKeyV2Request();
            param.setCode(code);
            param.setAccessToken(accessToken);
            // 发起请求
            String response = new Gson().toJson(SmartAppGetSessionKeyV2.getSessionKeyV2(param));
            // 将响应字符串解析为 JSON 对象
            ObjectMapper objectMapper = JsonUtils.getObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            // 检查是否有错误信息
            if (jsonNode.has("errno") && jsonNode.get("errno").asInt() != 0) {
                throw new HttpException("百度获取openid失败: " + jsonNode.toPrettyString());
            }
            jsonNode = jsonNode.path("data");
            if (!jsonNode.isMissingNode()) {
                String sessionKey = jsonNode.path("session_key").asText(null);
                // 设置到 Redis
                redisService.set(getSessionKey(), sessionKey);
                return jsonNode;
            }  else {
                throw new RuntimeException("百度获取openid未获取到有效的 data");
            }
        } catch (Exception e) {
            log.error("百度获取openid方法异常", e);
            throw new HttpException("百度获取openid方法异常: " + e.getMessage(), e);
        }
    }

    @Override
    public String getPhoneNumber(String code) {
        try {
            // 将响应字符串解析为 JSON 对象
            ObjectMapper objectMapper = JsonUtils.getObjectMapper();
            // 参数集合
            JsonNode dataJsonNode = objectMapper.readTree(code);
            // 获取encryptedData 和 iv
            String encryptedData = dataJsonNode.path("encryptedData").asText(null);
            String iv = dataJsonNode.path("iv").asText(null);
            // 获取sessionKey
            String sessionKey = (String) redisService.get(getSessionKey());
            // 解析手机号
            SmartAppDataDecrypt smartAppDataDecrypt = new SmartAppDataDecrypt();
            String response = smartAppDataDecrypt.decrypt(encryptedData, sessionKey, iv);
            JsonNode jsonNode = objectMapper.readTree(response);
            // 检查是否有错误信息
            String mobile = jsonNode.path("mobile").asText(null);
            if (mobile != null && !mobile.isEmpty()) {
                return mobile;
            } else {
                throw new RuntimeException("百度获取手机号未找到有效的 mobile: " + jsonNode.toPrettyString());
            }
        } catch (Exception e) {
            log.error("百度获取手机号方法异常", e);
            throw new HttpException("百度获取手机号方法异常: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, String> getPayment(Map<String, Object> paymentData) {
        Map<String, String> res = new HashMap<>(3);
        try {
            Map<String, Object> params = new HashMap<>(4);
            params.put("appKey", appletProperties.getPaymentAppKey());
            params.put("dealId", appletProperties.getDealId());
            params.put("tpOrderId", paymentData.get("outTradeNo"));
            BigDecimal totalAmount = (BigDecimal) paymentData.get("totalAmount");
            BigDecimal multipliedAmount = totalAmount.multiply(BigDecimal.valueOf(100));
            params.put("totalAmount", multipliedAmount);
            String rsaSign = RSASign.sign(params, appletProperties.getPrivateKey());
            res.put("rsaSign", rsaSign);
            res.put("appKey", appletProperties.getPaymentAppKey());
            res.put("dealId", appletProperties.getDealId());
            return res;
        } catch (Exception e) {
            log.error("微信下单方法异常", e);
            throw new HttpException("微信下单方法异常: " + e.getMessage(), e);
        }
    }

    @Override
    public Object signValidation(Map<String, String> params) {
        try {
            Map<String, Object> checkParams = new HashMap<>(5);
            checkParams.put("appKey", appletProperties.getPaymentAppKey());
            checkParams.put("dealId", appletProperties.getDealId());
            checkParams.put("tpOrderId", params.get("tpOrderId"));
            checkParams.put("totalAmount", params.get("totalAmount"));
            checkParams.put("rsaSign", params.get("rsaSign"));
            return RSASign.checkSign(checkParams, appletProperties.getPublicKey());
        } catch (Exception e) {
            log.error("百度签名验证失败: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean queryOrder(Map<String, String> queryData) {
        try {
            // 获取token
            String accessToken = getToken();
            // 请求参数
            FindByTpOrderIDRequest param  = new FindByTpOrderIDRequest();
            // access_token
            param.setAccessToken(accessToken);
            // 设置商户订单号
            param.setTpOrderID(queryData.get("tpOrderId"));
            // 百度收银台的支付服务 appKey
            param.setPmAppKey(appletProperties.getPaymentAppKey());
            // 发起请求
            String response = new Gson().toJson(SmartAppFindByTpOrderID.findByTpOrderId(param));
            // 将响应字符串解析为 JSON 对象
            ObjectMapper objectMapper = JsonUtils.getObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            // 检查是否有错误信息
            if (jsonNode.has("errno") && jsonNode.get("errno").asInt() != 0) {
                throw new HttpException("百度获取订单信息失败: " + jsonNode.toPrettyString());
            }
            jsonNode = jsonNode.path("data");
            if (!jsonNode.isMissingNode()) {
                int status = jsonNode.path("status").asInt(0);
                return status == 2;
            }  else {
                throw new RuntimeException("百度获取订单信息未获取到有效的 data");
            }
        } catch (Exception e) {
            log.error("百度查询订单方法异常: {}", e.getMessage());
            throw new HttpException("百度查询订单方法异常: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean refundOrder(Map<String, Object> refundData) {
        try {
            // 获取token
            String accessToken = getToken();
            // 请求参数
            ApplyOrderRefundRequest param  = new ApplyOrderRefundRequest();
            // 金额
            BigDecimal totalAmount = (BigDecimal) refundData.get("totalAmount");
            int applyRefundMoney = totalAmount.multiply(BigDecimal.valueOf(100)).intValueExact();
            param.setApplyRefundMoney(applyRefundMoney);
            // accessToken
            param.setAccessToken(accessToken);
            // 退款单号
            param.setBizRefundBatchID((String) refundData.get("refundNo"));
            // 是否跳过审核 1 是 0 否
            param.setIsSkipAudit(1);
            // 百度订单id
            param.setOrderID((Long) refundData.get("tradeNo"));
            // 退款原因
            param.setRefundReason("出票失败退款");
            // 退款类型 1：用户发起退款；2：开发者业务方客服退款；3：开发者服务异常退款。
            param.setRefundType(3);
            // 设置商户订单号
            param.setTpOrderID((String) refundData.get("outTradeNo"));
            // 百度订单userId
            param.setUserID((Long) refundData.get("baiduUserId"));
            // 百度收银台的支付服务 appKey
            param.setPmAppKey(appletProperties.getPaymentAppKey());
            // 发起请求
            String response = new Gson().toJson(SmartAppApplyOrderRefund.applyOrderRefund(param));
            // 将响应字符串解析为 JSON 对象
            ObjectMapper objectMapper = JsonUtils.getObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            // 检查是否有错误信息
            if (jsonNode.has("errno") && jsonNode.get("errno").asInt() != 0) {
                throw new HttpException("百度获取退款订单信息失败: " + jsonNode.toPrettyString());
            }
        } catch (Exception e) {
            log.error("百度退款方法异常: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean queryRefund(Map<String, String> queryData) {
        try {
            // 获取token
            String accessToken = getToken();
            FindOrderRefundRequest param  = new FindOrderRefundRequest();
            // accessToken
            param.setAccessToken(accessToken);
            // 设置商户订单号
            param.setTpOrderID(queryData.get("outTradeNo"));
            // 百度订单userId
            param.setUserID(Long.parseLong(queryData.get("baiduUserId")));
            // 百度收银台的支付服务 appKey
            param.setPmAppKey(appletProperties.getPaymentAppKey());
            // 发起请求
            String response = new Gson().toJson(SmartAppFindOrderRefund.findOrderRefund(param));
            // 将响应字符串解析为 JSON 对象
            ObjectMapper objectMapper = JsonUtils.getObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            // 检查是否有错误信息
            if (jsonNode.has("errno") && jsonNode.get("errno").asInt() != 0) {
                throw new HttpException("百度获取查询退款订单信息失败: " + jsonNode.toPrettyString());
            }
            int refundStatus = 0;
            JsonNode dataNode = jsonNode.path("data");
            // 遍历 "data" 数组中的每个对象
            if (dataNode.isArray()) {
                for (JsonNode item : dataNode) {
                    String bizRefundBatchId = item.path("bizRefundBatchId").asText();
                    if (queryData.get("refundNo").equals(bizRefundBatchId)) {
                        refundStatus = item.path("refundStatus").asInt();
                    }
                }
            }
            return refundStatus == 2;
        } catch (Exception e) {
            log.error("", e);
        }
        return false;
    }
}
