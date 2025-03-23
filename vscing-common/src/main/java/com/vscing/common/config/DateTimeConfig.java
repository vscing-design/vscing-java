package com.vscing.common.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * DateTimeConfig
 *
 * @author vscing
 * @date 2025/3/23 17:08
 */
@Configuration
public class DateTimeConfig implements WebMvcConfigurer {

  private static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
  private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
  private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

  @Override
  public void addFormatters(FormatterRegistry registry) {
    // LocalDateTime 转换器
    registry.addFormatterForFieldType(LocalDateTime.class, new org.springframework.format.Formatter<LocalDateTime>() {
      private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN);

      @NotNull
      @Override
      public LocalDateTime parse(@NotNull String text, @NotNull java.util.Locale locale) {
        return LocalDateTime.parse(text, formatter);
      }

      @NotNull
      @Override
      public String print(@NotNull LocalDateTime object, @NotNull java.util.Locale locale) {
        return formatter.format(object);
      }
    });

    // LocalDate 转换器
    registry.addFormatterForFieldType(LocalDate.class, new org.springframework.format.Formatter<LocalDate>() {
      private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);

      @NotNull
      @Override
      public LocalDate parse(@NotNull String text, @NotNull java.util.Locale locale) {
        return LocalDate.parse(text, formatter);
      }

      @NotNull
      @Override
      public String print(@NotNull LocalDate object, @NotNull java.util.Locale locale) {
        return formatter.format(object);
      }
    });

    // LocalTime 转换器
    registry.addFormatterForFieldType(LocalTime.class, new org.springframework.format.Formatter<LocalTime>() {
      private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT);

      @NotNull
      @Override
      public LocalTime parse(@NotNull String text, @NotNull java.util.Locale locale) {
        return LocalTime.parse(text, formatter);
      }

      @NotNull
      @Override
      public String print(@NotNull LocalTime object, @NotNull java.util.Locale locale) {
        return formatter.format(object);
      }
    });
  }
}
