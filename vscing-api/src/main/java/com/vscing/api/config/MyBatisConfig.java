package com.vscing.api.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis相关配置
 *
 * @date 2024/12/13 21:46
 * @auth vscing(vscing @ foxmail.com)
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.vscing.model.mapper"})
public class MyBatisConfig {
}
