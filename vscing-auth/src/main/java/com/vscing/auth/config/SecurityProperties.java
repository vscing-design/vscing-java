package com.vscing.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SecurityProperties
 *
 * @date 2024/12/14 16:25
 * @author vscing(vscing@foxmail.com)
 */
@Component
@ConfigurationProperties(prefix = "secure.ignored")
@Getter
@Setter
public class SecurityProperties {

    /**
     * 白名单 URL 集合
     */
    private List<String> urls;

    private Boolean enabled;

}
