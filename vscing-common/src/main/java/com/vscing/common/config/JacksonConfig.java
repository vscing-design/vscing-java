package com.vscing.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * JacksonConfig
 *
 * @author vscing
 * @date 2024/12/18 23:27
 */
@Configuration
public class JacksonConfig {

  private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
  private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
  private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer customizer() {
    return builder ->
        builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss")
            // long类型转string， 前端处理Long类型，数值过大会丢失精度
//                        .serializerByType(Long.class, ToStringSerializer.instance)
//                        .serializerByType(Long.TYPE, ToStringSerializer.instance)
            // 过滤null
//            .serializationInclusion(JsonInclude.Include.NON_NULL)
            //指定反序列化类型，也可以使用@JsonFormat(pattern = "yyyy-MM-dd")替代。主要是mvc接收日期时使用
            .deserializerByType(LocalTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
            .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            // 日期序列化，主要返回数据时使用
            .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
            .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
  }

  @Bean
  public ObjectMapper objectMapper() {

    ObjectMapper mapper = new ObjectMapper();

    JavaTimeModule module = new JavaTimeModule();

    // 配置 Jackson 序列化 BigDecimal 时使用的格式
    module.addSerializer(BigDecimal.class, ToStringSerializer.instance);

    // 配置 Jackson 序列化 long类型为String，解决后端返回的Long类型在前端精度丢失的问题
    module.addSerializer(BigInteger.class, ToStringSerializer.instance);
    module.addSerializer(Long.class, ToStringSerializer.instance);
    module.addSerializer(Long.TYPE, ToStringSerializer.instance);

    // 配置 Jackson 序列化 LocalDateTime、LocalDate、LocalTime 时使用的格式
    module.addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)));
    module.addSerializer(new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
    module.addSerializer(new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));


    // 配置 Jackson 反序列化 LocalDateTime、LocalDate、LocalTime 时使用的格式
    module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)));
    module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
    module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

    mapper.registerModule(module);
    return mapper;
  }

}
