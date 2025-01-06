package com.vscing.api.controller.v1;

import com.vscing.api.service.UserService;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.UserLoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * UserController
 *
 * @author vscing
 * @date 2025/1/6 12:23
 */
@Slf4j
@RestController
@RequestMapping("/v1/user")
@Tag(name = "平台用户接口", description = "平台用户接口")
public class UserController {

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Autowired
  private UserService userService;

  @PostMapping("/login")
  @Operation(summary = "小程序用户登录")
  public CommonResult<Object> login(@Validated @RequestBody UserLoginDto userLogin,
                                    BindingResult bindingResult,
                                    HttpServletRequest request) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    String token = userService.login(userLogin, request);
    if (token == null || token.isBlank()) {
      return CommonResult.failed("登陆失败");
    } else {
      Map<String, String> tokenMap = new HashMap<>(2);
      tokenMap.put("token", token);
      tokenMap.put("tokenHead", tokenHead);
      return CommonResult.success("登陆成功", tokenMap);
    }
  }

}
