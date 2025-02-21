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
   * appKey
   */
  private String appKey;

  /**
   * appSecret
   */
  private String appSecret;

  /**
   * paymentAppId
   */
  private String paymentAppId;

  /**
   * paymentAppKey
   */
  private String paymentAppKey;

  /**
   * dealId
   */
  private String dealId;

  /**
   * 平台公钥
   */
  private String publicKey;

  /**
   * 平台私钥
   */
  private String privateKey;

}
