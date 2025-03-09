package com.vscing.api.controller.v1;

import com.vscing.api.po.UserDetails;
import com.vscing.api.service.UserService;
import com.vscing.common.api.CommonResult;
import com.vscing.common.utils.JsonUtils;
import com.vscing.model.dto.UserInviteQrcodeDto;
import com.vscing.model.dto.UserLoginDto;
import com.vscing.model.mq.InviteMq;
import com.vscing.model.vo.UserApiLocationVo;
import com.vscing.model.vo.UserDetailVo;
import com.vscing.mq.config.FanoutRabbitMQConfig;
import com.vscing.mq.service.RabbitMQService;
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

  @Autowired
  private RabbitMQService rabbitMQService;

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
    try {
      String authToken = authHeader.substring(this.tokenHead.length());
      // 授权手机号
      String phone = userService.userPhone(userLogin, userData, authToken);
      if (phone != null && !phone.isEmpty()) {
        if (userLogin.getUuid() != null && !userLogin.getUuid().isEmpty()) {
          // 发送mq异步处理 同步出票信息
          InviteMq inviteMq = new InviteMq();
          inviteMq.setUserId(userData.getId());
          inviteMq.setInviteUserId(Long.valueOf(userLogin.getUuid()));
          String msg = JsonUtils.toJsonString(inviteMq);
          rabbitMQService.sendFanoutMessage(FanoutRabbitMQConfig.INVITE_QUEUE, msg);
        }
        return CommonResult.success("授权成功", phone);
      } else {
        return CommonResult.failed("授权失败");
      }
    } catch (Exception e) {
      log.error("授权手机号失败", e);
      return CommonResult.failed("请求错误");
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

  @PostMapping("/inviteQrcode")
  @Operation(summary = "推广二维码")
  public CommonResult<String> inviteQrcode(@Validated @RequestBody UserInviteQrcodeDto userInviteQrcodeDto,
                                           BindingResult bindingResult,
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
    try {
      String inviteQrcode = userService.inviteQrcode(userInviteQrcodeDto, userData);
      // data:image/png;base64,
      return CommonResult.success("获取成功", inviteQrcode);
    } catch (Exception e) {
      log.error("获取二维码失败", e);
      return CommonResult.failed("请求错误");
    }
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
