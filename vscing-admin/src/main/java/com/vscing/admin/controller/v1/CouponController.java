package com.vscing.admin.controller.v1;

import com.vscing.admin.po.AdminUserDetails;
import com.vscing.admin.service.CouponService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.CouponListDto;
import com.vscing.model.request.CouponCancelRequest;
import com.vscing.model.vo.CouponListVo;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CouponController
 *
 * @author vscing
 * @date 2025/3/23 01:11
 */
@Slf4j
@RestController
@RequestMapping("/v1/coupon")
@Tag(name = "优惠劵接口", description = "优惠劵接口")
public class CouponController {

  @Autowired
  private CouponService couponService;

  @GetMapping
  @Operation(summary = "优惠劵列表")
  public CommonResult<CommonPage<CouponListVo>> lists(@ParameterObject CouponListDto queryParam,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<CouponListVo> list = couponService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

  @PostMapping
  @Operation(summary = "优惠劵作废")
  public CommonResult<Object> cancel(@Validated @RequestBody CouponCancelRequest data,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal AdminUserDetails userInfo) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    // 操作人ID
    Long by = 0L;
    if(userInfo != null && userInfo.getUserId() != null) {
      by = userInfo.getUserId();
    }
    try {
      boolean is = couponService.couponCancel(data, by);
      if(is) {
        return CommonResult.success("优惠劵作废成功");
      }
      return CommonResult.failed("优惠劵作废失败");
    } catch (Exception e) {
      log.error("请求异常: {}", e.getMessage());
      return CommonResult.failed("请求异常");
    }
  }

}
