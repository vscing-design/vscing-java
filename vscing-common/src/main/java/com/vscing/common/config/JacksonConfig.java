package com.vscing.common.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.vscing.common.handler.BigNumberSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * JacksonConfig
 *
 * @author vscing
 * @date 2024/12/18 23:27
 */
@Slf4j
@Configuration
public class JacksonConfig {

  private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
  private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
  private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer customizer() {
    return builder -> {
      // 禁用默认类型信息
      // 关闭未知属性报错
      builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      // 过滤null
//      builder.serializationInclusion(JsonInclude.Include.NON_NULL);

      // 全局配置序列化返回 JSON 处理
      JavaTimeModule javaTimeModule = new JavaTimeModule();
      // long类型转string， 前端处理Long类型，数值过大会丢失精度
      javaTimeModule.addSerializer(Long.class, BigNumberSerializer.INSTANCE);
      javaTimeModule.addSerializer(Long.TYPE, BigNumberSerializer.INSTANCE);
      javaTimeModule.addSerializer(BigInteger.class, BigNumberSerializer.INSTANCE);
      // BigDecimal类型转string
      javaTimeModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
      // 配置 Jackson 序列化 LocalDateTime、LocalDate、LocalTime 时使用的格式
      javaTimeModule.addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)));
      javaTimeModule.addSerializer(new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
      javaTimeModule.addSerializer(new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
      // 配置 Jackson 反序列化 LocalDateTime、LocalDate、LocalTime 时使用的格式
      javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)));
      javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
      javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
      builder.modules(javaTimeModule);
      builder.timeZone(TimeZone.getDefault());
      log.info("初始化 jackson 配置");
    };
  }

}
