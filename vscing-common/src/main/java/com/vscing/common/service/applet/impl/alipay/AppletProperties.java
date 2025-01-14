package com.vscing.common.service.applet.impl.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * SupplierProperties
 *
 * @author vscing
 * @date 2025/1/7 16:05
 */
@Data
@Component("alipayAppletProperties")
@ConfigurationProperties(prefix = "alipay-applet")
public class AppletProperties {

  /**
   * appId
   */
  private String appId;

  /**
   * aes
   */
  private String aes;

  /**
   * publicKey
   */
  private String publicKey;

  /**
   * privateKey
   */
  private String privateKey;

  /**
   * appCertPath
  */
  private String appCertPath;

  /**
   * alipayPublicCertPath
   */
  private String alipayPublicCertPath;

  /**
   * rootCertPath
   */
  private String rootCertPath;

  public String getAbsolutePath(String path) throws IOException {
    Resource resource = new ClassPathResource(path);
    return resource.getFile().getAbsolutePath();
  }

}