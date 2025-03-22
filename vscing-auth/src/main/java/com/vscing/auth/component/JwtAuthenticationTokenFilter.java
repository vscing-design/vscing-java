package com.vscing.auth.component;

import com.vscing.auth.config.SecurityProperties;
import com.vscing.auth.service.VscingUserDetails;
import com.vscing.auth.service.VscingUserDetailsService;
import com.vscing.auth.util.JwtTokenUtil;
import com.vscing.common.service.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JwtValidationFilter
 *
 * @author vscing
 * @date 2024/12/14 16:41
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  @Autowired
  private VscingUserDetailsService vscingUserDetailsService;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Value("${jwt.tokenHeader}")
  private String tokenHeader;

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Value("${jwt.cachePrefix}")
  private String cachePrefix;

  @Autowired
  private RedisService redisService;

  @Autowired
  private SecurityProperties securityProperties;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  @NotNull HttpServletResponse response,
                                  @NotNull FilterChain chain) throws ServletException, IOException {
    List<String> unprotectedUrls = securityProperties.getUrls();
    String authHeader = request.getHeader(this.tokenHeader);
    // 如果请求路径在白名单中，则直接放行。其它请求路径需要校验。
    if (!unprotectedUrls.contains(request.getRequestURI()) && authHeader != null && authHeader.startsWith(this.tokenHead)) {
      String authToken = authHeader.substring(this.tokenHead.length());
      Long userId = jwtTokenUtil.getUserIdFromToken(authToken);
      if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        VscingUserDetails userDetails = this.vscingUserDetailsService.loadUserByUserId(userId);
        if (userDetails != null && jwtTokenUtil.validateToken(authToken, userDetails.getUserId())) {
          // 检查redis中是否有该token
          String redisKey = cachePrefix + userDetails.getUserId() + ":" + authToken;
          if (redisService.get(redisKey) != null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            log.info("当前登录用户ID:{}", userId);
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        }
      }
    }
    chain.doFilter(request, response);
  }
}
