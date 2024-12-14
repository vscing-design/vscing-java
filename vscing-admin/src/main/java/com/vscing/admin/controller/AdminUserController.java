package com.vscing.admin.controller;

import com.vscing.admin.dto.AdminUserLoginDto;
import com.vscing.admin.service.AdminUserService;
import com.vscing.common.api.CommonResult;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Autowired
  AdminUserService adminUserService;

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

}
