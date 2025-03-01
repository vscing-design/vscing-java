package com.vscing.admin.controller.v1;

import com.vscing.admin.po.AdminUserDetails;
import com.vscing.admin.service.BannerService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.BannerListDto;
import com.vscing.model.entity.Banner;
import com.vscing.model.vo.BannerListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * BannerController
 *
 * @author vscing
 * @date 2024/12/26 22:22
 */
@Slf4j
@RestController
@RequestMapping("/v1/banner")
@Tag(name = "轮播图接口", description = "轮播图接口")
public class BannerController {

  @Autowired
  private BannerService bannerService;

  @GetMapping
  @Operation(summary = "列表")
  public CommonResult<CommonPage<BannerListVo>> lists(@ParameterObject BannerListDto queryParam,
                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<BannerListVo> list = bannerService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @PostMapping
  @Operation(summary = "新增")
  public CommonResult<Object> add(@Validated @RequestBody Banner banner,
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
      banner.setCreatedBy(userInfo.getUserId());
    }
    try {
      long id = bannerService.created(banner);
      if (id == 0) {
        return CommonResult.failed("新增失败");
      } else {
        return CommonResult.success("新增成功");
      }
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

  @PutMapping
  @Operation(summary = "编辑")
  public CommonResult<Object> save(@Validated @RequestBody Banner banner,
                                   BindingResult bindingResult,
                                   @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (banner.getId() == null) {
      return CommonResult.validateFailed("ID不能为空");
    }
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    if(userInfo != null) {
      // 操作人ID
      banner.setUpdatedBy(userInfo.getUserId());
    }
    try {
      int result = bannerService.updated(banner);
      if (result == 0) {
        return CommonResult.failed("编辑失败");
      } else {
        return CommonResult.success("编辑成功");
      }
    } catch (Exception e) {
      log.error("请求错误: ", e);
      return CommonResult.failed("请求错误");
    }
  }

}
