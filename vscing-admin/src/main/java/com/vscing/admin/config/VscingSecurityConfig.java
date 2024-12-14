package com.vscing.admin.config;

import com.vscing.admin.service.AdminUserService;
import com.vscing.auth.service.VscingUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SecurityConfig
 *
 * @author vscing
 * @date 2024/12/14 16:41
 */
@Configuration
public class VscingSecurityConfig {

    @Autowired
    private AdminUserService adminUserService;

    @Bean
    public VscingUserDetailsService userDetailsService() {
        //获取登录用户信息
        return userId -> adminUserService.adminUserInfo(userId);
    }

}
