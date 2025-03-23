package com.vscing.api.controller.v1;

import com.vscing.api.po.UserDetails;
import com.vscing.api.service.CouponService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.CouponApiListDto;
import com.vscing.model.entity.Coupon;
import com.vscing.model.vo.UserDetailVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CouponController
 *
 * @author vscing
 * @date 2025/3/22 16:46
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
  public CommonResult<CommonPage<Coupon>> lists(@ParameterObject CouponApiListDto queryParam,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                @AuthenticationPrincipal UserDetails userInfo) {
    if(userInfo == null) {
      return CommonResult.failed("上下文异常");
    }
    UserDetailVo userData = userInfo.getUser();
    if (userData == null) {
      return CommonResult.failed("用户不存在");
    }
    queryParam.setUserId(userData.getId());
    List<Coupon> list = couponService.selectApiList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

}
