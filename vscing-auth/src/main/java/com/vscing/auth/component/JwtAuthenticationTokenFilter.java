package com.vscing.auth.component;

import com.vscing.auth.service.VscingUserDetails;
import com.vscing.auth.service.VscingUserDetailsService;
import com.vscing.auth.util.JwtTokenUtil;
import com.vscing.common.service.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain) throws ServletException, IOException {
    String authHeader = request.getHeader(this.tokenHeader);
    if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
      String authToken = authHeader.substring(this.tokenHead.length());
      Long userId = jwtTokenUtil.getUserIdFromToken(authToken);
      log.info("checking userId:{}", userId);
      if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        VscingUserDetails userDetails = this.vscingUserDetailsService.loadUserByUserId(userId);
        if (userDetails != null && jwtTokenUtil.validateToken(authToken, userDetails.getUserId())) {
          // 检查redis中是否有该token
          String redisKey = cachePrefix + userDetails.getUserId() + ":" + authToken;
          if (redisService.get(redisKey) != null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            log.info("authenticated user:{}", userId);
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        }
      }
    }
    chain.doFilter(request, response);
  }
}
