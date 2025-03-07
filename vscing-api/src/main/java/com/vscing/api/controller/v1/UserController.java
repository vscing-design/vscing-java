package com.vscing.api.controller.v1;

import com.vscing.api.po.UserDetails;
import com.vscing.api.service.UserService;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.UserLoginDto;
import com.vscing.model.vo.UserApiLocationVo;
import com.vscing.model.vo.UserDetailVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

  @Value("${jwt.tokenHeader}")
  private String tokenHeader;

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Autowired
  private UserService userService;

  @PostMapping("/testLogin")
  @Operation(summary = "测试小程序用户登录")
  public CommonResult<Object> testLogin() {
    String token = userService.testLogin();
    if (token == null || token.isBlank()) {
      return CommonResult.failed("登陆失败");
    } else {
      Map<String, String> tokenMap = new HashMap<>(2);
      tokenMap.put("token", token);
      tokenMap.put("tokenHead", tokenHead);
      return CommonResult.success("登陆成功", tokenMap);
    }
  }

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

  @PostMapping("/phone")
  @Operation(summary = "小程序用户授权手机号")
  public CommonResult<Object> phone(@Validated @RequestBody UserLoginDto userLogin,
                                    BindingResult bindingResult,
                                    HttpServletRequest request,
                                    @AuthenticationPrincipal UserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    if(userInfo == null) {
      return CommonResult.failed("上下文异常");
    }
    UserDetailVo userData = userInfo.getUser();
    if (userData == null) {
      return CommonResult.failed("用户不存在");
    }
    String authHeader = request.getHeader(this.tokenHeader);
    if(authHeader == null || !authHeader.startsWith(this.tokenHead)) {
      return CommonResult.failed("token不存在");
    }
    String authToken = authHeader.substring(this.tokenHead.length());
    // 授权手机号
    String phone = userService.userPhone(userLogin, userData, authToken);
    if (phone != null && !phone.isEmpty()) {
      return CommonResult.success("授权成功", phone);
    } else {
      return CommonResult.failed("授权失败");
    }
  }

  @PostMapping("/self")
  @Operation(summary = "用户详细信息")
  public CommonResult<UserDetailVo> self(@AuthenticationPrincipal UserDetails userInfo) {
    if(userInfo == null) {
      return CommonResult.failed("上下文异常");
    }
    UserDetailVo userData = userInfo.getUser();
    if (userData == null) {
      return CommonResult.failed("用户不存在");
    }
    return CommonResult.success(userData);
  }

  @GetMapping("/location")
  @Operation(summary = "用户当前经纬度获取")
  public CommonResult<UserApiLocationVo> location(HttpServletRequest request) {
    return CommonResult.success(userService.getLocation(request));
  }

  @GetMapping("/logout")
  @Operation(summary = "用户登出")
  public CommonResult<Object> login(HttpServletRequest request, @AuthenticationPrincipal UserDetails userInfo) {
    if(userInfo == null) {
      return CommonResult.failed("上下文异常");
    }
    UserDetailVo userData = userInfo.getUser();
    if (userData == null) {
      return CommonResult.failed("用户不存在");
    }
    String authHeader = request.getHeader(this.tokenHeader);
    if(authHeader == null || !authHeader.startsWith(this.tokenHead)) {
      return CommonResult.failed("token不存在");
    }
    String authToken = authHeader.substring(this.tokenHead.length());
    if (userService.logout(userData, authToken)) {
      return CommonResult.success("登出成功");
    } else {
      return CommonResult.failed("登出失败");
    }
  }

}
