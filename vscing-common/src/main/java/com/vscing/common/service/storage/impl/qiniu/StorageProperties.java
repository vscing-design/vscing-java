package com.vscing.common.service.storage.impl.qiniu;

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
@Component("qiniuStorageProperties")
@ConfigurationProperties(prefix = "qiniu-storage")
public class StorageProperties {

  /**
   * appId
   */
  // private String appId;

}
