package com.vscing.auth.component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * RestfulAccessDeniedHandler
 *
 * @author vscing
 * @date 2024/12/14 17:52
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
                     AccessDeniedException accessDeniedException) throws IOException, ServletException {
    // 设置响应状态码为 403
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    // 设置响应内容类型为 JSON
    response.setContentType("application/json;charset=UTF-8");
    // 返回自定义错误信息
    response.getWriter().write("{\"code\":" + HttpServletResponse.SC_FORBIDDEN + ",\"result\": null, \"message\":\"禁止访问\"}");
  }
}
