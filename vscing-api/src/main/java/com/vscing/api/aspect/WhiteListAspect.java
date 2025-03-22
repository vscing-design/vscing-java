package com.vscing.api.aspect;

import com.vscing.api.annotate.WhiteListAnnotate;
import com.vscing.api.config.WhiteListConfig;
import com.vscing.common.utils.ServletUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * WhiteListAspect
 *
 * @author vscing
 * @date 2025/3/22 16:08
 */
@Aspect
@Component
public class WhiteListAspect {

  @Autowired
  private WhiteListConfig whiteListConfig;

  @Around("@annotation(whiteListAnnotate)")
  public Object whitelistCheck(ProceedingJoinPoint joinPoint, WhiteListAnnotate whiteListAnnotate) throws Throwable {
    // 获取客户端IP地址
    String clientIp = ServletUtils.getClientIP();

    List<String> allowedIps = whiteListConfig.getTypes().get(whiteListAnnotate.type());

    if (allowedIps == null || !allowedIps.contains(clientIp)) { // 检查客户端IP是否在白名单内
      throw new SecurityException("Access Denied");
    }

    return joinPoint.proceed(); // 继续执行目标方法
  }

}
