package com.vscing.common.service.applet.impl.baidu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * AppletProperties
 *
 * @author vscing
 * @date 2025/1/7 16:05
 */
@Data
@Component("baiduAppletProperties")
@ConfigurationProperties(prefix = "baidu-applet")
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
   * 商户号
   */
  private String merchantId;

  /**
   * 商户API私钥路径
   */
  private String privateKeyPath;

  /**
   * 微信支付公钥的存放路径
   */
  private String publicKeyPath;

  /**
   * 微信支付公钥ID
   */
  private String publicKeyId;

  /**
   * 商户证书序列号
   */
  private String merchantSerialNumber;

  /**
   * 商户APIV3密钥
   */
  private String apiV3Key;



}
