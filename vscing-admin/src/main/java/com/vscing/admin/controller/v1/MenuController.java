package com.vscing.admin.controller.v1;

import com.vscing.admin.po.AdminUserDetails;
import com.vscing.admin.service.MenuService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.MenuListDto;
import com.vscing.model.entity.Menu;
import com.vscing.model.vo.MenuTreeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MenuController
 *
 * @author vscing
 * @date 2024/12/22 01:24
 */
@RestController
@RequestMapping("/v1/menu")
@Tag(name = "系统菜单接口", description = "系统菜单接口")
public class MenuController {

  private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

  @Autowired
  private MenuService menuService;

  @GetMapping
  @Operation(summary = "列表")
  public CommonResult<CommonPage<Menu>> lists(MenuListDto queryParam,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<Menu> list = menuService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @GetMapping("/tree")
  @Operation(summary = "树形列表")
  public CommonResult<List<MenuTreeVo>> lists(MenuListDto queryParam) {
    List<MenuTreeVo> list = menuService.getAllList(queryParam);
    return CommonResult.success(list);
  }

  @GetMapping("/{id}")
  @Operation(summary = "详情")
  public CommonResult<Menu> details(@PathVariable("id") Long id) {
    Menu menu = menuService.getDetails(id);
    if (menu == null) {
      return CommonResult.failed("信息不存在");
    }
    return CommonResult.success(menu);
  }

  @PostMapping
  @Operation(summary = "新增")
  public CommonResult<Object> add(@Validated @RequestBody Menu menu,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 操作人ID
    if(userInfo != null) {
      // 操作人ID
      menu.setCreatedBy(userInfo.getUserId());
    }
    try {
      long id = menuService.created(menu);
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

  @PutMapping
  @Operation(summary = "编辑")
  public CommonResult<Object> save(@Validated @RequestBody Menu menu,
                                   BindingResult bindingResult,
                                   @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (menu.getId() == null) {
      return CommonResult.validateFailed("ID不能为空");
    }
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    if(userInfo != null) {
      // 操作人ID
      menu.setUpdatedBy(userInfo.getUserId());
    }
    try {
      long id = menuService.updated(menu);
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

  @DeleteMapping("/{id}")
  @Operation(summary = "删除")
  public CommonResult<Object> delete(@PathVariable Long id, @AuthenticationPrincipal AdminUserDetails userInfo) {
    if(id == null) {
      return CommonResult.validateFailed("参数错误");
    }
    long result = menuService.deleted(id, userInfo.getUserId());
    if (result != 0) {
      return CommonResult.success("删除成功");
    } else {
      return CommonResult.failed("删除失败");
    }
  }


}
