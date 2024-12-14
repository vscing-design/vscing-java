package com.vscing.auth.config;

import com.vscing.auth.component.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig
 *
 * @author vscing
 * @date 2024/12/14 16:41
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 忽略的路径
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers(securityProperties.getUrls().toArray(new String[0])).permitAll() // 使用配置文件中的白名单URL
            .anyRequest().authenticated()); // 其他所有请求需要认证

        http
            // 禁用CSRF保护
            .csrf(AbstractHttpConfigurer::disable)
            // 由于使用的是JWT，CORS保护也可以禁用
            .cors(AbstractHttpConfigurer::disable)
            // 禁用session
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 添加自定义未授权和未登录结果返回
        http.exceptionHandling(exceptionHandling -> exceptionHandling
            .accessDeniedHandler(restfulAccessDeniedHandler) // 自定义处理未授权
            .authenticationEntryPoint(restfulAuthenticationEntryPoint)) // 自定义处理未登录
            // 添加JWT过滤器
            .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 动态权限配置时添加动态权限校验过滤器
//        if (dynamicSecurityService != null) {
//            http.addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);
//        }

        return http.build();
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 时间成本，默认是 3
        int timeCost = 16;
        // 内存成本，默认是 16384 KB (16 MB)
        int memoryCost = 65536;
        // 并行度，默认是 1
        int parallelism = 2;
        // 哈希长度，默认是 16 字节
        int hashLength = 32;
        // 盐长度，默认是 16 字节
        int saltLength = 16;

        return new Argon2PasswordEncoder(timeCost, memoryCost, parallelism, hashLength, saltLength);
    }
}
