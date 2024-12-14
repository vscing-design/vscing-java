package com.vscing.auth.config;

import com.vscing.auth.component.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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

//    @PostMapping("/login")
//    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
//        try {
//            // 尝试认证
//            Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
//            );
//
//            // 创建并返回 JWT
//            String jwt = tokenProvider.createToken(authentication);
//
//            return ResponseEntity.ok(new JwtResponse(jwt));
//        } catch (AuthenticationException e) {
//            throw new BadCredentialsException("Invalid username or password");
//        }
//    }
}
