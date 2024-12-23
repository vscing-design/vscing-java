package com.vscing.admin.controller.v1;

import com.vscing.admin.po.AdminUserDetails;
import com.vscing.admin.service.AdminUserService;
import com.vscing.auth.util.JwtTokenUtil;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.common.util.MapstructUtils;
import com.vscing.common.util.RequestUtil;
import com.vscing.model.dto.AdminUserListDto;
import com.vscing.model.dto.AdminUserLoginDto;
import com.vscing.model.dto.AdminUserSaveDto;
import com.vscing.model.entity.AdminUser;
import com.vscing.model.request.AdminUserEditRequest;
import com.vscing.model.request.AdminUserSaveRequest;
import com.vscing.model.vo.AdminUserDetailVo;
import com.vscing.model.vo.AdminUserListVo;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * AdminUserController
 *
 * @author vscing
 * @date 2024/12/14 23:15
 */
@Slf4j
@RestController
@RequestMapping("/v1/admin")
@Tag(name = "系统管理员登陆接口", description = "系统管理员登陆接口")
public class AdminUserController {

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private AdminUserService adminUserService;

  @PostMapping("/login")
  @Operation(summary = "后台用户登录")
  @Parameter(name = "username", description = "用户名")
  @Parameter(name = "password", description = "密码")
  public CommonResult<Object> login(@Validated @RequestBody AdminUserLoginDto adminUserLogin,
                                    BindingResult bindingResult,
                                    HttpServletRequest request) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    String token = adminUserService.login(adminUserLogin.getUsername(), adminUserLogin.getPassword(), request);
    if (token == null || token.isBlank()) {
      return CommonResult.failed("登陆失败");
    } else {
      Map<String, String> tokenMap = new HashMap<>(2);
      tokenMap.put("token", token);
      tokenMap.put("tokenHead", tokenHead);
      return CommonResult.success("登陆成功", tokenMap);
    }
  }

  @GetMapping("/users")
  @Operation(summary = "后台用户列表")
  public CommonResult<CommonPage<AdminUserListVo>> users(AdminUserListDto queryParam,
                                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<AdminUser> userList = adminUserService.getList(queryParam, pageSize, pageNum);
    // 直接调用改进后的 Mapper 方法进行转换
    List<AdminUserListVo> list = userList.stream()
        .map(adminUser -> MapstructUtils.convert(adminUser, AdminUserListVo.class))
        .collect(Collectors.toList());
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/users/{id}")
  @Operation(summary = "后台用户详情")
  public CommonResult<AdminUserDetailVo> details(@PathVariable("id") Long id) {
    AdminUser adminUser = adminUserService.getDetails(id);
    if (adminUser == null) {
      return CommonResult.failed("用户不存在");
    }
    // 关联角色
    List<Object> relatedRole = new ArrayList<>();
    // 关联菜单
    List<Object> relatedMenu = new ArrayList<>();
    // 直接调用改进后的 Mapper 方法进行转换
    AdminUserDetailVo detail = MapstructUtils.convert(adminUser, AdminUserDetailVo.class);
    detail.setRelatedRole(relatedRole);
    detail.setRelatedMenu(relatedMenu);
    return CommonResult.success(detail);
  }

  @PostMapping("/users")
  @Operation(summary = "后台用户新增")
  public CommonResult<Object> users(@Validated @RequestBody AdminUserSaveRequest adminUserSave,
                                        BindingResult bindingResult,
                                        HttpServletRequest request,
                                        @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 验证密码和确认密码是否匹配
    if (!Objects.equals(adminUserSave.getPassword(), adminUserSave.getConfirmPassword())) {
      return CommonResult.validateFailed("两次密码不一致");
    }
    // 直接调用改进后的 Mapper 方法进行转换
    AdminUserSaveDto adminUser = MapstructUtils.convert(adminUserSave, AdminUserSaveDto.class);
    // 增加ip地址、操作人ID
    adminUser.setLastIp(RequestUtil.getRequestIp(request));
    // 操作人ID
    if(userInfo != null && userInfo.getUserId() != null) {
      adminUser.setCreatedBy(userInfo.getUserId());
    }
    try {
      boolean res = adminUserService.created(adminUser);
      if (res) {
        return CommonResult.success("新增成功");
      }
      return CommonResult.failed("新增失败");
    } catch (Exception e) {
      log.error("请求错误: " + e.getMessage());
      return CommonResult.failed("请求错误");
    }
  }

  @PutMapping("/users")
  @Operation(summary = "后台用户编辑")
  public CommonResult<Object> users(@Validated @RequestBody AdminUserEditRequest adminUserEdit,
                                        BindingResult bindingResult,
                                        @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 直接调用改进后的 Mapper 方法进行转换
    AdminUserSaveDto adminUser = MapstructUtils.convert(adminUserEdit, AdminUserSaveDto.class);
    // 操作人ID
    if(userInfo != null && userInfo.getUserId() != null) {
      adminUser.setUpdatedBy(userInfo.getUserId());
    }
    boolean res = adminUserService.updated(adminUser);
    try {
      if (res) {
        return CommonResult.success("编辑成功");
      }
      return CommonResult.failed("编辑失败");
    } catch (Exception e) {
      log.error("请求错误: " + e.getMessage());
      return CommonResult.failed("请求错误");
    }
  }

  @DeleteMapping("/users/{id}")
  @Operation(summary = "后台用户删除")
  public CommonResult<Object> users(@PathVariable Long id, @AuthenticationPrincipal AdminUserDetails userInfo) {
    if(id == null) {
      return CommonResult.validateFailed("参数错误");
    }
    if(jwtTokenUtil.isSuperAdmin(id)) {
      return CommonResult.failed("超级管理员不可删除！");
    }
    // 操作人ID
    Long byId = 0L;
    if(userInfo != null && userInfo.getUserId() != null) {
      byId = userInfo.getUserId();
    }
    try {
      boolean res = adminUserService.deleted(id, byId);
      if (res) {
        return CommonResult.success("删除成功");
      }
      return CommonResult.failed("删除失败");
    } catch (Exception e) {
      log.error("请求错误: " + e.getMessage());
      return CommonResult.failed("请求错误");
    }
  }


}
