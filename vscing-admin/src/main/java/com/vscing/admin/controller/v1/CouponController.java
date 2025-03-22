package com.vscing.admin.controller.v1;

import com.vscing.admin.service.CouponService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.CouponListDto;
import com.vscing.model.entity.Coupon;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
  @Operation(summary = "列表")
  public CommonResult<CommonPage<Coupon>> lists(@ParameterObject CouponListDto queryParam,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<Coupon> list = couponService.getList(queryParam, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(list));
  }

}
