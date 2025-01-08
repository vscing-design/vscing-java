package com.vscing.common.service.applet.impl.wechat;

import cn.hutool.http.HttpException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.common.service.OkHttpService;
import com.vscing.common.service.RedisService;
import com.vscing.common.service.applet.AppletService;
import com.vscing.common.utils.JsonUtils;
import com.vscing.common.utils.StringUtils;
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

  public String getKey() {
    if (this.key == null) {
      this.key = String.format("%s.access_token.%s.%s.%d",
          CACHE_KEY_PREFIX, appletProperties.getAppId(), appletProperties.getAppSecret(), appletProperties.getStable() ? 1 : 0);
    }
    return this.key;
  }

  public String getToken() {
    String token = (String) redisService.get(this.getKey());

    // 检查 token 是否为 null 或空字符串
    if (StringUtils.isNotBlank(token)) {
      return token;
    }

    return this.refresh();
  }

  public String refresh() {
    return appletProperties.getStable() ? this.getStableAccessToken(false) : this.getAccessToken();
  }

  public String getStableAccessToken(boolean forceRefresh) {
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
        throw new HttpException("WeChat API returned an error: " + jsonNode.toPrettyString());
      }
      // 提取 access_token 和过期时间
      String accessToken = jsonNode.path("access_token").asText();
      Integer expiresIn = jsonNode.path("expires_in").asInt();

      if (StringUtils.isNotBlank(accessToken) && expiresIn > 0) {
        // 设置到 Redis 并返回 access_token
        redisService.set(getKey(), accessToken, expiresIn);
        log.info("Stable access token refreshed successfully.");
        return accessToken;
      } else {
        throw new HttpException("Failed to get stable access_token or invalid expires_in value: " + jsonNode.toPrettyString());
      }
    } catch (IOException e) {
      log.error("Error during HTTP request for stable access token.", e);
      throw new HttpException("Error refreshing stable access token: " + e.getMessage(), e);
    } catch (Exception e) {
      log.error("Unexpected error while refreshing stable access token.", e);
      throw new HttpException("Unexpected error refreshing stable access token: " + e.getMessage(), e);
    }
  }

  public String getAccessToken() {
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
        throw new HttpException("WeChat API returned an error: " + jsonNode.toPrettyString());
      }
      // 提取 access_token 和过期时间
      String accessToken = jsonNode.path("access_token").asText();
      Integer expiresIn = jsonNode.path("expires_in").asInt();

      if (StringUtils.isNotBlank(accessToken) && expiresIn > 0) {
        // 设置到 Redis 并返回 access_token
        redisService.set(getKey(), accessToken, expiresIn);
        log.info("Stable access token refreshed successfully.");
        return accessToken;
      } else {
        throw new HttpException("Failed to get stable access_token or invalid expires_in value: " + jsonNode.toPrettyString());
      }
    } catch (IOException e) {
      log.error("Error during HTTP request for stable access token.", e);
      throw new HttpException("Error refreshing stable access token: " + e.getMessage(), e);
    } catch (Exception e) {
      log.error("Unexpected error while refreshing stable access token.", e);
      throw new HttpException("Unexpected error refreshing stable access token: " + e.getMessage(), e);
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
      // 将响应字符串解析为 JSON 对象
      ObjectMapper objectMapper = JsonUtils.getObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(response);
      // 检查是否有错误信息
      if (jsonNode.has("errcode") && jsonNode.get("errcode").asInt() != 0) {
        throw new HttpException("WeChat API returned an error: " + jsonNode.toPrettyString());
      }
      // 导航到 phone_info 节点并获取 purePhoneNumber
      JsonNode phoneInfoNode = jsonNode.path("phone_info");
      if (!phoneInfoNode.isMissingNode()) {
        String purePhoneNumber = phoneInfoNode.path("purePhoneNumber").asText(null);
        if (purePhoneNumber != null && !purePhoneNumber.isEmpty()) {
          return purePhoneNumber;
        } else {
          throw new RuntimeException("未找到有效的 purePhoneNumber");
        }
      } else {
        throw new RuntimeException("未找到 phone_info 节点");
      }
    } catch (IOException e) {
      log.error("Error during HTTP request for stable access token.", e);
      throw new HttpException("Error refreshing stable access token: " + e.getMessage(), e);
    } catch (Exception e) {
      log.error("Unexpected error while refreshing stable access token.", e);
      throw new HttpException("Unexpected error refreshing stable access token: " + e.getMessage(), e);
    }
  }

}
