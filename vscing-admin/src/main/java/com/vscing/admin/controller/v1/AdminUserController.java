package com.vscing.admin.controller.v1;

import com.vscing.admin.po.AdminUserDetails;
import com.vscing.auth.util.JwtTokenUtil;
import com.vscing.common.api.CommonPage;
import com.vscing.model.dto.AdminUserListDto;
import com.vscing.model.dto.AdminUserLoginDto;
import com.vscing.model.entity.AdminUser;
import com.vscing.admin.service.AdminUserService;
import com.vscing.common.api.CommonResult;
import com.vscing.model.struct.AdminUserMapper;
import com.vscing.model.vo.AdminUserDetailVo;
import com.vscing.model.vo.AdminUserListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.vscing.common.util.RequestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * AdminUserController
 *
 * @author vscing
 * @date 2024/12/14 23:15
 */

@RestController
@RequestMapping("/v1/admin")
@Tag(name = "系统管理员登陆接口", description = "系统管理员登陆接口")
public class AdminUserController {

  private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

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
//    List<AdminUserListVo> list = AdminUserMapper.INSTANCE.adminUserToAdminUserListVos(userList);

    List<AdminUserListVo> list = userList.stream()
        .map(AdminUserMapper.INSTANCE::adminUserToAdminUserListVo)
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

    AdminUserDetailVo detail = AdminUserMapper.INSTANCE.adminUserToAdminUserDetailVo(adminUser);
    detail.setRelatedRole(relatedRole);
    detail.setRelatedMenu(relatedMenu);
    return CommonResult.success(detail);
  }

  @PostMapping("/users")
  @Operation(summary = "后台用户新增")
  public CommonResult<Object> users(@Validated @RequestBody AdminUser adminUser,
                                        BindingResult bindingResult,
                                        HttpServletRequest request,
                                        @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 增加ip地址、操作人ID
    adminUser.setLastIp(RequestUtil.getRequestIp(request));
    adminUser.setCreatedBy(userInfo.getUserId());
    try {
      long id = adminUserService.created(adminUser);
      if (id == 0) {
        return CommonResult.failed("新增失败");
      } else {
        return CommonResult.success("新增成功");
      }
    } catch (Exception e) {
      // 记录异常日志
      e.printStackTrace();
      return CommonResult.failed("系统错误: " + e.getMessage());
    }
  }

  @PutMapping("/users")
  @Operation(summary = "后台用户编辑")
  public CommonResult<Object> users(@Validated @RequestBody AdminUser adminUser,
                                        BindingResult bindingResult,
                                        @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    if(userInfo != null) {
      // 操作人ID
      adminUser.setUpdatedBy(userInfo.getUserId());
    }
    try {
      long id = adminUserService.updated(adminUser);
      if (id == 0) {
        return CommonResult.failed("编辑失败");
      } else {
        return CommonResult.success("编辑成功");
      }
    } catch (Exception e) {
      // 记录异常日志
      e.printStackTrace();
      return CommonResult.failed("系统错误: " + e.getMessage());
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
    long result = adminUserService.deleted(id, userInfo.getUserId());
    if (result != 0) {
      return CommonResult.success("删除成功");
    } else {
      return CommonResult.failed("删除失败");
    }
  }


}
