package com.vscing.api.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * WhiteListAnnotate 白名单注解
 *
 * @author vscing
 * @date 2025/3/22 16:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WhiteListAnnotate {

  String type() default ""; // 定义类型的属性

}
