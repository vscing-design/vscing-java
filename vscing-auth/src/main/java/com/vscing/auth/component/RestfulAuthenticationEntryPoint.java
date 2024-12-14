package com.vscing.auth.component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * AuthenticationEntryPoint
 *
 * @author vscing
 * @date 2024/12/14 17:53
 */
@Component
public class RestfulAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
                       AuthenticationException authException) throws IOException, ServletException {
    // 设置响应状态码为 401
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    // 设置响应内容类型为 JSON
    response.setContentType("application/json;charset=UTF-8");
    // 返回自定义错误信息
    response.getWriter().write("{\"code\":" + HttpServletResponse.SC_UNAUTHORIZED + ",\"result\": null, \"message\":\"未授权\"}");
  }
}