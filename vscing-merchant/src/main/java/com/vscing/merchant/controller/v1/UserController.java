package com.vscing.merchant.controller.v1;

import com.vscing.common.api.CommonResult;
import com.vscing.merchant.po.MerchantDetails;
import com.vscing.merchant.service.UserService;
import com.vscing.model.request.MerchantUserLoginRequest;
import com.vscing.model.vo.MerchantDetailVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
 * @date 2025/4/19 17:22
 */
@Slf4j
@RestController
@RequestMapping("/v1/user")
@Tag(name = "商户接口", description = "商户接口")
public class UserController {

  @Value("${jwt.tokenHeader}")
  private String tokenHeader;

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Autowired
  private UserService userService;

  @PostMapping("/login")
  @Operation(summary = "后台用户登录")
  @Parameter(name = "username", description = "用户名")
  @Parameter(name = "password", description = "密码")
  public CommonResult<Object> login(@Validated @RequestBody MerchantUserLoginRequest record,
                                    BindingResult bindingResult,
                                    HttpServletRequest request) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    String token = userService.login(record.getUsername(), record.getPassword(), request);
    if (token == null || token.isBlank()) {
      return CommonResult.failed("登陆失败");
    } else {
      Map<String, String> tokenMap = new HashMap<>(2);
      tokenMap.put("token", token);
      tokenMap.put("tokenHead", tokenHead);
      return CommonResult.success("登陆成功", tokenMap);
    }
  }

  @GetMapping("/logout")
  @Operation(summary = "后台用户登出")
  public CommonResult<Object> login(HttpServletRequest request, @AuthenticationPrincipal MerchantDetails userInfo) {
    if(userInfo == null) {
      return CommonResult.failed("上下文异常");
    }
    MerchantDetailVo merchantDetailVo = userInfo.getMerchant();
    if (merchantDetailVo == null) {
      return CommonResult.failed("用户不存在");
    }
    String authHeader = request.getHeader(this.tokenHeader);
    if(authHeader == null || !authHeader.startsWith(this.tokenHead)) {
      return CommonResult.failed("token不存在");
    }
    String authToken = authHeader.substring(this.tokenHead.length());
    if (userService.logout(merchantDetailVo, authToken)) {
      return CommonResult.success("登出成功");
    } else {
      return CommonResult.failed("登出失败");
    }
  }

}
