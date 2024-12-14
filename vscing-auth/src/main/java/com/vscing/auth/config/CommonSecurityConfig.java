package com.vscing.auth.config;

import com.vscing.auth.component.*;
import com.vscing.auth.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author vscing (vscing@foxmail.com)
 * @date 2024-12-14 20:47:37
*/
@Configuration
public class CommonSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
//        // 时间成本，默认是 3
//        int timeCost = 16;
//        // 内存成本，默认是 16384 KB (16 MB)
//        int memoryCost = 65536;
//        // 并行度，默认是 1
//        int parallelism = 2;
//        // 哈希长度，默认是 16 字节
//        int hashLength = 32;
//        // 盐长度，默认是 16 字节
//        int saltLength = 16;
//
//        return new Argon2PasswordEncoder(timeCost, memoryCost, parallelism, hashLength, saltLength);
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityProperties SecurityProperties() {
        return new SecurityProperties();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint() { return new RestfulAuthenticationEntryPoint(); }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }


}
