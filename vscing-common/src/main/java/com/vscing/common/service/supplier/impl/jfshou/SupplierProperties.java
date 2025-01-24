package com.vscing.common.service.supplier.impl.jfshou;

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
@Component
@ConfigurationProperties(prefix = "supplier-jfshou")
public class SupplierProperties {

  /**
   * 根路径
   */
  private String baseUrl;

  /**
   * userNo
   */
  private String userNo;

  /**
   * key
   */
  private String key;

}
