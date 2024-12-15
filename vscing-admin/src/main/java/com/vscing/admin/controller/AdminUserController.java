package com.vscing.admin.controller;

import cn.hutool.core.util.IdUtil;
import com.vscing.admin.po.AdminUserDetails;
import com.vscing.model.dto.AdminUserLoginDto;
import com.vscing.model.entity.AdminUser;
import com.vscing.admin.service.AdminUserService;
import com.vscing.common.api.CommonResult;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;
import com.vscing.common.util.RequestUtil;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * AdminUserController
 *
 * @author vscing
 * @date 2024/12/14 23:15
 */

@RestController
@RequestMapping("/v1/adminUser")
@Tag(name = "系统管理员登陆接口", description = "系统管理员登陆接口")
public class AdminUserController {

  private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AdminUserService adminUserService;

  @PostMapping("/login")
  @Parameter(name = "username", description = "用户名")
  @Parameter(name = "password", description = "密码")
  public CommonResult<Object> login(@Validated @RequestBody AdminUserLoginDto adminUserLogin) {
    String token = adminUserService.login(adminUserLogin.getUsername(), adminUserLogin.getPassword());
    if (token.isEmpty()) {
      return CommonResult.failed("登陆失败");
    } else {
      Map<String, String> tokenMap = new HashMap<>();
      tokenMap.put("token", token);
      tokenMap.put("tokenHead", tokenHead);
      return CommonResult.success("登陆成功", tokenMap);
    }
  }

  @PostMapping("/register")
  public CommonResult<Object> register(@RequestBody AdminUser adminUser, HttpServletRequest request, @AuthenticationPrincipal AdminUserDetails userInfo) {

    logger.info(String.valueOf(userInfo.getUserId()));

    // 检查用户名和密码是否为空
    if (adminUser.getUsername() == null || adminUser.getUsername().trim().isEmpty() ||
        adminUser.getPassword() == null || adminUser.getPassword().trim().isEmpty()) {
      return CommonResult.validateFailed("用户名和密码不能为空");
    }
    adminUser.setLastIp(RequestUtil.getRequestIp(request));
    try {
      long id = adminUserService.createAdminUser(adminUser);
      if (id == 0) {
        return CommonResult.failed("注册失败");
      } else {
        return CommonResult.success("注册成功", String.valueOf(id));
      }
    } catch (Exception e) {
      // 记录异常日志
      e.printStackTrace();
      return CommonResult.failed("系统错误: " + e.getMessage());
    }
  }



}
