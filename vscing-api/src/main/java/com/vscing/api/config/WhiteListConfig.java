package com.vscing.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * WhiteListConfig
 *
 * @author vscing
 * @date 2025/3/22 16:16
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "whitelist")
public class WhiteListConfig {

  private Map<String, List<String>> types;

}
