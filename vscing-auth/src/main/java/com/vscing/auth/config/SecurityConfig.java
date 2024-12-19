package com.vscing.auth.config;

import com.vscing.auth.component.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.Customizer;

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
    public SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            // 指定这个安全链只处理这些路径
            .securityMatcher("/v3/api-docs/**", "/swagger-ui/**")
            .authorizeHttpRequests((authorize) -> authorize
                .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            // 禁用CSRF保护，因为Swagger UI可能不需要它
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 如果不需要会话管理，可以禁用

        return http.build();
    }
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // TODO：这段可以单独提出来做一个 SecurityFilterChain
        if (!securityProperties.getEnabled()) {
            // 如果安全校验开关关闭，则不应用任何安全配置
            http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);

            return http.build();
        }

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

    @Bean(name = "swaggerUserDetailsService")
    public UserDetailsService swaggerUserDetailsService(PasswordEncoder encoder) {
        // 内存用户存储，实际项目中应使用数据库或其他持久化方式
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        // 使用 BCrypt 编码器来编码密码
        manager.createUser(User.withUsername("admin")
            // 安全地编码密码
            .password(encoder.encode("admin@123456"))
            .roles("USER")
            .build());

        return manager;
    }

}
