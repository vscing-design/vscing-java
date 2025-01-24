package com.vscing.common.service.applet.impl.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * SupplierProperties
 *
 * @author vscing
 * @date 2025/1/7 16:05
 */
@Data
@Component("wechatAppletProperties")
@ConfigurationProperties(prefix = "wechat-applet")
public class AppletProperties {

  /**
   * appId
   */
  private String appId;

  /**
   * appSecret
   */
  private String appSecret;

  /**
   * 稳定版
  */
  private Boolean stable;

  /**
   * 商户号
   */
  private String merchantId;

  /**
   * 商户API私钥路径
   */
  private String privateKeyPath;

  /**
   * 商户证书序列号
   */
  private String merchantSerialNumber;

  /**
   * 商户APIV3密钥
   */
  private String apiV3Key;



}
