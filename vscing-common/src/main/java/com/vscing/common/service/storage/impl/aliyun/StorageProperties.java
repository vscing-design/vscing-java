package com.vscing.common.service.storage.impl.aliyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * StorageProperties
 *
 * @author vscing
 * @date 2025/1/7 16:05
 */
@Data
@Component("aliyunStorageProperties")
@ConfigurationProperties(prefix = "aliyun-oss")
public class StorageProperties {

  /**
   * endpoint
   */
  private String endpoint;

  /**
   * accessKeyId
   */
  private String accessKeyId;

  /**
   * accessKeySecret
   */
  private String accessKeySecret;

  /**
   * bucketName
   */
  private String bucketName;

  /**
   * domain
   */
  private String domain;

  /**
   * region
   */
  private String region;

}
