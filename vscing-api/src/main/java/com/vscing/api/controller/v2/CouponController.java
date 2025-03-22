package com.vscing.api.controller.v2;

import com.vscing.api.annotate.WhiteListAnnotate;
import com.vscing.api.service.CouponService;
import com.vscing.common.api.CommonResult;
import com.vscing.model.request.CouponDetailsRequest;
import com.vscing.model.request.CouponRequest;
import com.vscing.model.vo.CouponApiDetailsVo;
import com.vscing.mq.service.RabbitMQService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CouponController
 *
 * @author vscing
 * @date 2025/3/22 16:46
 */
@Slf4j
@RestController
@RequestMapping("/v2/coupon")
@Tag(name = "优惠劵接口", description = "优惠劵接口")
public class CouponController {

  @Autowired
  private RabbitMQService rabbitMQService;

  @Autowired
  private CouponService couponService;

  @PostMapping
  @WhiteListAnnotate(type = "coupon")
  @Operation(summary = "新增优惠券")
  public CommonResult<Boolean> save(@Validated @RequestBody CouponRequest data,
                                      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    return CommonResult.success(couponService.save(data));
  }

  @GetMapping
  @WhiteListAnnotate(type = "coupon")
  @Operation(summary = "优惠券详情")
  public CommonResult<CouponApiDetailsVo> details(@Validated @RequestBody CouponDetailsRequest data,
                                                  BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 获取第一个错误信息，如果需要所有错误信息
      String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
      return CommonResult.validateFailed(errorMessage);
    }
    return CommonResult.success(couponService.details(data));
  }

}
